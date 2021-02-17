package pl.motokomando.healthcare.controller.patient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.GetClient;
import pl.motokomando.healthcare.controller.utils.JsonPatchOperation;
import pl.motokomando.healthcare.controller.utils.LocalDateAdapter;
import pl.motokomando.healthcare.controller.utils.LocalDateTimeAdapter;
import pl.motokomando.healthcare.controller.utils.PatchClient;
import pl.motokomando.healthcare.controller.utils.PatchUtils;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.controller.utils.WebResponseUtils;
import pl.motokomando.healthcare.model.base.utils.PatientDetails;
import pl.motokomando.healthcare.model.patient.PatientModel;
import pl.motokomando.healthcare.model.patient.utils.PatientAppointmentsPagedResponse;
import pl.motokomando.healthcare.model.patient.utils.PatientAppointmentsTableRecord;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENT;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENT_APPOINTMENTS;

public class PatientController {

    private final PatientModel patientModel;

    public PatientController(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public Void getPatientDetails() throws Exception {
        HttpResponse response = sendGetPatientDetailsRequest();
        extractPatientDetails(EntityUtils.toString(response.getEntity()));
        return null;
    }

    private HttpResponse sendGetPatientDetailsRequest() throws Exception {
        WebClient client = GetClient.builder()
                .path(PATIENT)
                .pathVariable("id", String.valueOf(patientModel.getPatientId()))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebResponseUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    public Void handleUpdatePatientDetailsButtonClicked(PatientDetails patientDetails) throws Exception {
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap =
                PatchUtils.createAddOperationsMap(patientModel.getPatientDetails(), patientDetails);
        sendUpdatePatientDetailsRequest(operationsMap);
        patientModel.setPatientDetails(patientDetails);
        return null;
    }

    private void extractPatientDetails(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        setPatientRegistrationDate(jsonObject);
        setPatientDetails(jsonObject);
    }

    private void setPatientRegistrationDate(JsonObject jsonObject) {
        JsonObject json = jsonObject.getAsJsonObject("basicInfo");
        String registrationDate = json.get("registrationDate").getAsString();
        patientModel.setPatientRegistrationDate(LocalDateTime.parse(registrationDate));
    }

    private void setPatientDetails(JsonObject jsonObject) {
        JsonObject json = jsonObject.getAsJsonObject("patientDetails");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        PatientDetails patientDetails = gson.fromJson(json, PatientDetails.class);
        patientModel.setPatientDetails(patientDetails);
    }

    private void sendUpdatePatientDetailsRequest(Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap) throws Exception {
        WebClient client = PatchClient.builder()
                .path(PATIENT)
                .pathVariable("id", String.valueOf(patientModel.getPatientId()))
                .operations(operationsMap)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT) {
            WebResponseUtils.mapErrorResponseAsException(response);
        }
    }

    public Void updatePatientAppointmentsTablePageData() throws Exception {
        int page = patientModel.getPatientAppointmentsTableCurrentPage();
        int size = patientModel.getTableCountPerPage();
        HttpResponse response = sendGetPatientAppointmentsPagedRequest(page, size);
        Map<String, String> headers = WebResponseUtils.extractPageHeaders(response);
        setPatientAppointmentsTablePageDetails(headers);
        HttpEntity responseBody = response.getEntity();
        List<PatientAppointmentsTableRecord> tableContent = Collections.emptyList();
        if (responseBody != null) {
            tableContent = createPatientAppointmentsTableContent(EntityUtils.toString(responseBody));
        }
        patientModel.setPatientAppointmentsTablePageContent(tableContent);
        return null;
    }

    public void updatePatientAppointmentsTableCurrentPage(Integer pageNumber) {
        patientModel.setPatientAppointmentsTableCurrentPage(pageNumber);
    }

    private void setPatientAppointmentsTablePageDetails(Map<String, String> headers) {
        Integer currentPage = Integer.valueOf(headers.getOrDefault(CURRENT_PAGE, "1"));
        Integer totalPages = Integer.valueOf(headers.getOrDefault(TOTAL_PAGES, "1"));
        patientModel.setPatientAppointmentsTableCurrentPage(currentPage);
        patientModel.setPatientAppointmentsTableTotalPages(totalPages);
    }

    private List<PatientAppointmentsTableRecord> createPatientAppointmentsTableContent(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<PatientAppointmentsPagedResponse>>(){}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        List<PatientAppointmentsPagedResponse> recordList = gson.fromJson(jsonArray, listType);
        return mapPatientAppointmentsPagedResponseToBaseRecord(recordList);
    }

    private HttpResponse sendGetPatientAppointmentsPagedRequest(int page, int size) throws Exception {
        WebClient client = GetClient.builder()
                .path(PATIENT_APPOINTMENTS)
                .pathVariable("id", String.valueOf(patientModel.getPatientId()))
                .parameter("page", String.valueOf(page))
                .parameter("size", String.valueOf(size))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT && statusCode != SC_OK) {
            WebResponseUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private List<PatientAppointmentsTableRecord> mapPatientAppointmentsPagedResponseToBaseRecord(List<PatientAppointmentsPagedResponse> response) {
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return response
                .stream()
                .map(e -> new PatientAppointmentsTableRecord(
                        e.getId(),
                        new SimpleStringProperty(e.getAppointmentDate().format(dateFormatter)),
                        new SimpleStringProperty(e.getAppointmentStatus().getName())))
                .collect(Collectors.toList());
    }

}
