package pl.motokomando.healthcare.controller.appointment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.DeleteClient;
import pl.motokomando.healthcare.controller.utils.GetClient;
import pl.motokomando.healthcare.controller.utils.JsonPatchOperation;
import pl.motokomando.healthcare.controller.utils.PatchClient;
import pl.motokomando.healthcare.controller.utils.PostClient;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.controller.utils.WebUtils;
import pl.motokomando.healthcare.model.appointment.AppointmentModel;
import pl.motokomando.healthcare.model.appointment.utils.MedicineResponse;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;
import pl.motokomando.healthcare.model.appointment.utils.PrescriptionMedicinesTableRecord;
import pl.motokomando.healthcare.model.utils.DoctorBasic;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.BILL;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.BILLS;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTOR;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.MEDICINE;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.MEDICINES;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENT_APPOINTMENT;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PRESCRIPTION;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PRESCRIPTIONS;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PRESCRIPTION_MEDICINE;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PRESCRIPTION_MEDICINES;

public class AppointmentController {

    private final AppointmentModel appointmentModel;

    public AppointmentController(AppointmentModel appointmentModel) {
        this.appointmentModel = appointmentModel;
    }

    public Void handleMedicinesSearchAction(String query) throws Exception {
        HttpResponse response = sendSearchMedicinesRequest(query);
        HttpEntity responseBody = response.getEntity();
        List<MedicinesTableRecord> tableContent = Collections.emptyList();
        if (response.getStatusLine().getStatusCode() != SC_NOT_FOUND) {
            tableContent = createMedicinesTableContent(EntityUtils.toString(responseBody));
        }
        appointmentModel.setMedicinesTableContent(tableContent);
        return null;
    }

    public Void getAppointmentDetails() throws Exception {
        HttpResponse response = sendGetAppointmentDetailsRequest();
        HttpEntity responseBody = response.getEntity();
        extractAppointmentDetails(EntityUtils.toString(responseBody));
        return null;
    }

    public Void handleUpdatePrescriptionNotesButtonClicked(String notes) throws Exception {
        checkPrescriptionExistence();
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap =
                WebUtils.createAddOperationsMap(Collections.singletonMap("notes", notes));
        sendUpdatePrescriptionRequest(operationsMap);
        appointmentModel.setPrescriptionNotes(notes);
        return null;
    }

    public Void handleUpdateBillAmountButtonClicked(String billAmount) throws Exception {
        checkBillExistence();
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap =
                WebUtils.createAddOperationsMap(Collections.singletonMap("amount", billAmount));
        sendUpdateBillRequest(operationsMap);
        appointmentModel.setBillAmount(billAmount);
        return null;
    }

    public Void handleAddPrescriptionMedicines(List<String> medicineList) throws Exception {
        checkPrescriptionExistence();
        for (String productNDC : medicineList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("productNDC", productNDC);
            String body = new Gson().toJson(jsonObject);
            HttpResponse response = sendAddPrescriptionMedicineRequest(body);
            Integer medicineId = WebUtils.extractHeaderResourceId(response);
            appointmentModel.getPrescriptionMedicineList().add(medicineId);
        }
        return null;
    }

    public Void handleRemovePrescriptionMedicines(List<Integer> medicineList) throws Exception {
        for (Integer medicineId : medicineList) {
            sendRemovePrescriptionMedicineRequest(medicineId);
            appointmentModel.getPrescriptionMedicineList().remove(medicineId);
        }
        return null;
    }

    public Void updatePrescriptionMedicinesTableContent() throws Exception {
        List<PrescriptionMedicinesTableRecord> tableContent = new ArrayList<>();
        for (Integer medicineId : appointmentModel.getPrescriptionMedicineList()) {
            HttpResponse prescriptionMedicineDetailsResponse = sendGetPrescriptionMedicineDetailsRequest(medicineId);
            String productNDC = extractProductNDC(EntityUtils.toString(prescriptionMedicineDetailsResponse.getEntity()));
            HttpResponse medicineResponse = sendGetMedicineRequest(productNDC);
            HttpEntity responseBody = medicineResponse.getEntity();
            PrescriptionMedicinesTableRecord record = createPrescriptionMedicinesTableRecord
                    (medicineId, EntityUtils.toString(responseBody));
            tableContent.add(record);
        }
        appointmentModel.setPrescriptionMedicinesTableContent(tableContent);
        return null;
    }

    private PrescriptionMedicinesTableRecord createPrescriptionMedicinesTableRecord(Integer medicineId, String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        MedicineResponse medicineResponse = new Gson().fromJson(jsonObject, MedicineResponse.class);
        return mapMedicineResponseToPrescriptionMedicinesTableRecord(medicineId, medicineResponse);
    }

    private String extractProductNDC(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonObject.get("productNDC").getAsString();
    }

    private HttpResponse sendGetPrescriptionMedicineDetailsRequest(Integer medicineId) throws Exception {
        WebClient client = GetClient.builder()
                .path(PRESCRIPTION_MEDICINE)
                .pathVariable("prescriptionId", String.valueOf(appointmentModel.getPrescriptionId()))
                .pathVariable("medicineId", String.valueOf(medicineId))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private void sendRemovePrescriptionMedicineRequest(Integer medicineId) throws Exception {
        WebClient client = DeleteClient.builder()
                .path(PRESCRIPTION_MEDICINE)
                .pathVariable("prescriptionId", String.valueOf(appointmentModel.getPrescriptionId()))
                .pathVariable("medicineId", String.valueOf(medicineId))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT) {
            WebUtils.mapErrorResponseAsException(response);
        }
    }

    private HttpResponse sendAddPrescriptionMedicineRequest(String body) throws Exception {
        WebClient client = PostClient.builder()
                .path(PRESCRIPTION_MEDICINES)
                .pathVariable("id", String.valueOf(appointmentModel.getPrescriptionId()))
                .body(body)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_CREATED) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private void sendUpdateAppointmentRequest(
            Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap) throws Exception {
        WebClient client = PatchClient.builder()
                .path(PATIENT_APPOINTMENT)
                .pathVariable("patientId", String.valueOf(appointmentModel.getPatientId()))
                .pathVariable("appointmentId", String.valueOf(appointmentModel.getAppointmentId()))
                .operations(operationsMap)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT) {
            WebUtils.mapErrorResponseAsException(response);
        }
    }

    private void sendUpdatePrescriptionRequest(
            Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap) throws Exception {
        WebClient client = PatchClient.builder()
                .path(PRESCRIPTION)
                .pathVariable("id", String.valueOf(appointmentModel.getPrescriptionId()))
                .operations(operationsMap)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT) {
            WebUtils.mapErrorResponseAsException(response);
        }
    }

    private void sendUpdateBillRequest(
            Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap) throws Exception {
        WebClient client = PatchClient.builder()
                .path(BILL)
                .pathVariable("id", String.valueOf(appointmentModel.getBillId()))
                .operations(operationsMap)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT) {
            WebUtils.mapErrorResponseAsException(response);
        }
    }

    private void checkPrescriptionExistence() throws Exception {
        if (appointmentModel.getPrescriptionId() != null) {
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("expirationDate", LocalDate.now().plusMonths(1).format(ISO_DATE));
        String body = new Gson().toJson(jsonObject);
        HttpResponse response = sendCreateResourceRequest(PRESCRIPTIONS, body);
        Integer prescriptionId = WebUtils.extractHeaderResourceId(response);
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap =
                WebUtils.createAddOperationsMap(Collections.singletonMap("prescriptionId", String.valueOf(prescriptionId)));
        sendUpdateAppointmentRequest(operationsMap);
        appointmentModel.setPrescriptionId(prescriptionId);
    }

    private void checkBillExistence() throws Exception {
        if (appointmentModel.getBillId() != null) {
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", "0");
        String body = new Gson().toJson(jsonObject);
        HttpResponse response = sendCreateResourceRequest(BILLS, body);
        Integer billId = WebUtils.extractHeaderResourceId(response);
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap =
                WebUtils.createAddOperationsMap(Collections.singletonMap("billId", String.valueOf(billId)));
        sendUpdateAppointmentRequest(operationsMap);
        appointmentModel.setBillId(billId);
    }


    private HttpResponse sendCreateResourceRequest(String path, String body) throws Exception {
        WebClient client = PostClient.builder()
                .path(path)
                .body(body)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_CREATED) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private void extractAppointmentDetails(String responseBody) throws Exception {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        setDoctorDetails(jsonObject.getAsJsonObject("doctor"));
        if (jsonObject.has("bill")) {
            setBillId(jsonObject.getAsJsonObject("bill"));
        }
        if (jsonObject.has("prescription")) {
            setPrescriptionDetails(jsonObject.getAsJsonObject("prescription"));
        }
    }

    private void setBillId(JsonObject jsonObject) {
        Integer billId = jsonObject.get("id").getAsInt();
        String billAmount = jsonObject.get("amount").getAsString();
        LocalDateTime issueDate = LocalDateTime.parse(jsonObject.get("issueDate").getAsString(), ISO_DATE_TIME);
        appointmentModel.setBillId(billId);
        appointmentModel.setBillAmount(billAmount);
        appointmentModel.setBillIssueDate(issueDate);
    }

    private void setDoctorDetails(JsonObject jsonObject) throws Exception {
        Integer doctorId = jsonObject.get("id").getAsInt();
        getDoctorDetails(doctorId);
    }

    private void getDoctorDetails(Integer doctorId) throws Exception {
        HttpResponse response = sendGetDoctorDetailsRequest(doctorId);
        DoctorBasic doctorDetails = extractDoctorDetails(EntityUtils.toString(response.getEntity()));
        appointmentModel.setDoctorDetails(doctorDetails);
    }

    private DoctorBasic extractDoctorDetails(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return new Gson().fromJson(jsonObject, DoctorBasic.class);
    }

    private void setPrescriptionDetails(JsonObject jsonObject) {
        if (jsonObject.has("medicines")) {
            setPrescriptionMedicinesList(jsonObject.getAsJsonArray("medicines"));
        }
        JsonObject details = jsonObject.getAsJsonObject("details");
        Integer prescriptionId = details.get("id").getAsInt();
        LocalDateTime issueDate = LocalDateTime.parse(details.get("issueDate").getAsString(), ISO_DATE_TIME);
        LocalDate expirationDate = LocalDate.parse(details.get("expirationDate").getAsString(), ISO_DATE);
        appointmentModel.setPrescriptionId(prescriptionId);
        appointmentModel.setPrescriptionExpirationDate(expirationDate);
        appointmentModel.setPrescriptionIssueDate(issueDate);
        if (details.has("notes")) {
            String notes = details.get("notes").getAsString();
            appointmentModel.setPrescriptionNotes(notes);
        }
    }

    private void setPrescriptionMedicinesList(JsonArray jsonArray) {
        List<Integer> prescriptionMedicineList = appointmentModel.getPrescriptionMedicineList();
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            prescriptionMedicineList.add(jsonObject.get("id").getAsInt());
        }
    }

    private List<MedicinesTableRecord> createMedicinesTableContent(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<MedicineResponse>>(){}.getType();
        List<MedicineResponse> recordList = new Gson().fromJson(jsonArray, listType);
        return mapMedicinesResponseToMedicinesTableRecord(recordList);
    }

    private HttpResponse sendGetDoctorDetailsRequest(Integer doctorId) throws Exception {
        WebClient client = GetClient.builder()
                .path(DOCTOR)
                .pathVariable("id", String.valueOf(doctorId))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private HttpResponse sendGetAppointmentDetailsRequest() throws Exception {
        WebClient client = GetClient.builder()
                .path(PATIENT_APPOINTMENT)
                .pathVariable("patientId", String.valueOf(appointmentModel.getPatientId()))
                .pathVariable("appointmentId", String.valueOf(appointmentModel.getAppointmentId()))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private HttpResponse sendSearchMedicinesRequest(String query) throws Exception {
        WebClient client = GetClient.builder()
                .path(MEDICINES)
                .parameter("query", query)
                .parameter("limit", String.valueOf(appointmentModel.getTableCount()))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK && statusCode != SC_NOT_FOUND) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private HttpResponse sendGetMedicineRequest(String productNDC) throws Exception {
        WebClient client = GetClient.builder()
                .path(MEDICINE)
                .pathVariable("productNDC", productNDC)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private PrescriptionMedicinesTableRecord mapMedicineResponseToPrescriptionMedicinesTableRecord(
            Integer medicineId, MedicineResponse response) {
        MedicinesTableRecord tableRecord = createMedicinesTableRecord(response);
        return new PrescriptionMedicinesTableRecord(medicineId, tableRecord);
    }

    private List<MedicinesTableRecord> mapMedicinesResponseToMedicinesTableRecord(List<MedicineResponse> response) {
        return response
                .stream()
                .map(this::createMedicinesTableRecord)
                .collect(Collectors.toList());
    }

    private MedicinesTableRecord createMedicinesTableRecord(MedicineResponse response) {
        return new MedicinesTableRecord(
                new SimpleStringProperty(response.getProductNDC()),
                new SimpleStringProperty(response.getManufacturer()),
                new SimpleStringProperty(response.getProductName()),
                new SimpleStringProperty(response.getGenericName()),
                new SimpleObjectProperty<>(response.getProductType()),
                new SimpleListProperty<>(FXCollections.observableArrayList(
                        Optional.ofNullable(response.getActiveIngredients()).orElse(Collections.emptyList()))),
                new SimpleObjectProperty<>(response.getAdministrationRoute()),
                new SimpleStringProperty(response.getDosageForm()),
                new SimpleObjectProperty<>(response.getPackagingVariants()));
    }

}
