package pl.motokomando.healthcare.controller.patient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.GetClient;
import pl.motokomando.healthcare.controller.utils.LocalDateTimeAdapter;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.controller.utils.WebResponseUtils;
import pl.motokomando.healthcare.model.patient.PatientModel;
import pl.motokomando.healthcare.model.patient.utils.PatientAppointmentsPagedResponse;
import pl.motokomando.healthcare.model.patient.utils.PatientAppointmentsTableRecord;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENT_APPOINTMENTS;

public class PatientController {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final PatientModel patientModel;

    public PatientController(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public Void updatePatientAppointmentsTablePageData() throws Exception {
        int page = patientModel.getPatientAppointmentsTableCurrentPage();
        int size = patientModel.getTableCountPerPage();
        HttpResponse response = getPatientAppointmentsPagedResponse(page, size);
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

    private HttpResponse getPatientAppointmentsPagedResponse(int page, int size) throws Exception {
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
        return response
                .stream()
                .map(e -> new PatientAppointmentsTableRecord(
                        e.getId(),
                        new SimpleStringProperty(e.getAppointmentDate().format(dateFormatter)),
                        new SimpleStringProperty(e.getAppointmentStatus().getName())))
                .collect(Collectors.toList());
    }

}
