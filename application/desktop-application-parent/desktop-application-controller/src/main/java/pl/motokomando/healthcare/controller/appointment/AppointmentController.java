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
import pl.motokomando.healthcare.model.appointment.utils.MedicinesResponse;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;
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
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTOR;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.MEDICINES;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENT_APPOINTMENTS;
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
        extractAppointmentDetails(EntityUtils.toString(response.getEntity()));
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

    public Void handleAddPrescriptionMedicine(String productNDC) throws Exception {
        checkPrescriptionExistence();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("productNDC", productNDC);
        String body = new Gson().toJson(jsonObject);
        HttpResponse response = sendAddPrescriptionMedicineRequest(body);
        Integer medicineId = WebUtils.extractHeaderResourceId(response);
        appointmentModel.getPrescriptionMedicineList().add(medicineId);
        return null;
    }

    public Void handleRemovePrescriptionMedicine(Integer medicineId) throws Exception {
        sendRemovePrescriptionMedicineRequest(medicineId);
        appointmentModel.getPrescriptionMedicineList().remove(medicineId);
        return null;
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

    private void checkPrescriptionExistence() throws Exception {
        if (appointmentModel.getPrescriptionId() != null) {
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("expirationDate", LocalDate.now().plusDays(30).format(ISO_DATE));
        String body = new Gson().toJson(jsonObject);
        HttpResponse response = sendCreatePrescriptionRequest(body);
        Integer prescriptionId = WebUtils.extractHeaderResourceId(response);
        appointmentModel.setPrescriptionId(prescriptionId);
    }

    private HttpResponse sendCreatePrescriptionRequest(String body) throws Exception {
        WebClient client = PostClient.builder()
                .path(PRESCRIPTIONS)
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
        appointmentModel.setBillId(billId);
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
        Type listType = new TypeToken<ArrayList<MedicinesResponse>>(){}.getType();
        List<MedicinesResponse> recordList = new Gson().fromJson(jsonArray, listType);
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
                .path(PATIENT_APPOINTMENTS)
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

    private List<MedicinesTableRecord> mapMedicinesResponseToMedicinesTableRecord(List<MedicinesResponse> response) {
        return response
                .stream()
                .map(e -> new MedicinesTableRecord(
                        new SimpleStringProperty(e.getProductNDC()),
                        new SimpleStringProperty(e.getManufacturer()),
                        new SimpleStringProperty(e.getProductName()),
                        new SimpleStringProperty(e.getGenericName()),
                        new SimpleObjectProperty<>(e.getProductType()),
                        new SimpleListProperty<>(FXCollections.observableArrayList(e.getActiveIngredients())),
                        new SimpleObjectProperty<>(e.getAdministrationRoute()),
                        new SimpleStringProperty(e.getDosageForm()),
                        new SimpleObjectProperty<>(e.getPackagingVariants())))
                .collect(Collectors.toList());
    }

}
