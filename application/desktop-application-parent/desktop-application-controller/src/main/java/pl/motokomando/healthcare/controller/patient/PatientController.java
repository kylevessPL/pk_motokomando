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
import pl.motokomando.healthcare.controller.utils.LocalDateAdapter;
import pl.motokomando.healthcare.controller.utils.LocalDateTimeAdapter;
import pl.motokomando.healthcare.controller.utils.PostClient;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.controller.utils.WebUtils;
import pl.motokomando.healthcare.model.base.utils.PatientDetails;
import pl.motokomando.healthcare.model.patient.PatientModel;
import pl.motokomando.healthcare.model.patient.utils.DoctorAppointment;
import pl.motokomando.healthcare.model.patient.utils.DoctorBasic;
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

import static java.lang.Integer.MAX_VALUE;
import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTORS;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTOR_APPOINTMENTS;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENT;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENTS;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENT_APPOINTMENTS;
import static pl.motokomando.healthcare.model.patient.utils.AppointmentStatus.CANCELLED;

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

    public Void handleUpdatePatientDetailsButtonClicked(PatientDetails patientDetails) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        JsonObject jsonObject = (JsonObject) gson.toJsonTree(patientDetails);
        jsonObject.addProperty("id", patientModel.getPatientId());
        String body = gson.toJson(jsonObject);
        WebUtils.sendUpdatePersonRequest(PATIENTS, body);
        patientModel.setPatientDetails(patientDetails);
        return null;
    }

    public Void getAllDoctors() throws Exception {
        HttpResponse response = WebUtils.sendGetPageRequest(DOCTORS, Collections.emptyMap(), 1, MAX_VALUE);
        HttpEntity responseBody = response.getEntity();
        List<DoctorBasic> doctorBasicList = Collections.emptyList();
        if (responseBody != null) {
            doctorBasicList = createDoctorBasicList(EntityUtils.toString(responseBody));
        }
        patientModel.setDoctorBasicList(doctorBasicList);
        return null;
    }

    public Optional<List<DoctorAppointment>> getDoctorScheduledAppointments(Integer doctorId, LocalDate startDate, LocalDate endDate) throws Exception {
        HttpResponse response = sendGetDoctorAppointmentsRequest(doctorId, startDate, endDate);
        HttpEntity responseBody = response.getEntity();
        List<DoctorAppointment> doctorAppointmentList = null;
        if (responseBody != null) {
            doctorAppointmentList = createDoctorScheduledAppointmentList(EntityUtils.toString(responseBody));
        }
        return Optional.ofNullable(doctorAppointmentList);
    }

    public Void handleScheduleAppointmentButtonClicked(Integer doctorId, LocalDateTime date) throws Exception {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("doctorId", doctorId);
        jsonObject.addProperty("appointmentDate", date.format(ISO_DATE_TIME));
        String body = new Gson().toJson(jsonObject);
        sendScheduleAppointmentRequest(body);
        return null;
    }

    public Void updatePatientAppointmentsTablePageData() throws Exception {
        int page = patientModel.getPatientAppointmentsTableCurrentPage();
        int size = patientModel.getTableCountPerPage();
        Map<String, String> pathVariables = Collections.singletonMap("id", String.valueOf(patientModel.getPatientId()));
        HttpResponse response = WebUtils.sendGetPageRequest(PATIENT_APPOINTMENTS, pathVariables, page, size);
        Map<String, String> headers = WebUtils.extractPageHeaders(response);
        setPatientAppointmentsTablePageDetails(headers);
        HttpEntity responseBody = response.getEntity();
        List<PatientAppointmentsTableRecord> tableContent = Collections.emptyList();
        if (responseBody != null) {
            tableContent = createPatientAppointmentsTableContent(EntityUtils.toString(responseBody));
        }
        patientModel.setPatientAppointmentsTablePageContent(tableContent);
        return null;
    }

    private void sendScheduleAppointmentRequest(String body) throws Exception {
        WebClient client = PostClient.builder()
                .path(PATIENT_APPOINTMENTS)
                .pathVariable("id", String.valueOf(patientModel.getPatientId()))
                .body(body)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_CREATED) {
            WebUtils.mapErrorResponseAsException(response);
        }
    }

    private List<DoctorAppointment> createDoctorScheduledAppointmentList(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<DoctorAppointment>>(){}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        List<DoctorAppointment> doctorAppointmentList = gson.fromJson(jsonArray, listType);
        return doctorAppointmentList
                .stream()
                .filter(e -> !e.getAppointmentStatus().equals(CANCELLED))
                .collect(Collectors.toList());
    }

    private HttpResponse sendGetDoctorAppointmentsRequest(Integer doctorId, LocalDate startDate, LocalDate endDate) throws Exception {
        WebClient client = GetClient.builder()
                .path(DOCTOR_APPOINTMENTS)
                .pathVariable("id", String.valueOf(doctorId))
                .parameter("startDate", startDate.format(ISO_DATE))
                .parameter("endDate", endDate.format(ISO_DATE))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private HttpResponse sendGetPatientDetailsRequest() throws Exception {
        WebClient client = GetClient.builder()
                .path(PATIENT)
                .pathVariable("id", String.valueOf(patientModel.getPatientId()))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
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

    private List<DoctorBasic> createDoctorBasicList(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<DoctorBasic>>(){}.getType();
        return new Gson().fromJson(jsonArray, listType);
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
