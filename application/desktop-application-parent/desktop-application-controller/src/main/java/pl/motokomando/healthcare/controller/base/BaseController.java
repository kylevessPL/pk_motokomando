package pl.motokomando.healthcare.controller.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.LocalDateAdapter;
import pl.motokomando.healthcare.controller.utils.WebUtils;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.base.utils.DoctorDetails;
import pl.motokomando.healthcare.model.base.utils.PatientDetails;
import pl.motokomando.healthcare.model.base.utils.PersonTableRecord;
import pl.motokomando.healthcare.model.utils.PersonPagedResponse;
import pl.motokomando.healthcare.model.utils.SessionStore;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTORS;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.PATIENTS;

public class BaseController {

    private final BaseModel baseModel;

    private final SessionStore sessionStore = SessionStore.getInstance();

    public BaseController(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    public Void handleAddDoctorButtonClicked(DoctorDetails doctorDetails) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(doctorDetails);
        WebUtils.sendUpdatePersonRequest(DOCTORS, body);
        return null;
    }

    public Void handleAddPatientButtonClicked(PatientDetails patientDetails) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String body = gson.toJson(patientDetails);
        WebUtils.sendUpdatePersonRequest(PATIENTS, body);
        return null;
    }

    public Void updateDoctorsTablePageData() throws Exception {
        int page = baseModel.getDoctorsTableCurrentPage();
        int size = baseModel.getTableCountPerPage();
        HttpResponse response = WebUtils.sendGetPageRequest(DOCTORS, Collections.emptyMap(), page, size);
        Map<String, String> headers = WebUtils.extractPageHeaders(response);
        setDoctorsTablePageDetails(headers);
        HttpEntity responseBody = response.getEntity();
        List<PersonTableRecord> tableContent = Collections.emptyList();
        if (responseBody != null) {
            tableContent = createPersonTableContent(EntityUtils.toString(responseBody));
        }
        baseModel.setDoctorsTablePageContent(tableContent);
        return null;
    }

    public Void updatePatientsTablePageData() throws Exception {
        int page = baseModel.getPatientsTableCurrentPage();
        int size = baseModel.getTableCountPerPage();
        HttpResponse response = WebUtils.sendGetPageRequest(PATIENTS, Collections.emptyMap(), page, size);
        Map<String, String> headers = WebUtils.extractPageHeaders(response);
        setPatientsTablePageDetails(headers);
        HttpEntity responseBody = response.getEntity();
        List<PersonTableRecord> tableContent = Collections.emptyList();
        if (responseBody != null) {
            tableContent = createPersonTableContent(EntityUtils.toString(responseBody));
        }
        baseModel.setPatientsTablePageContent(tableContent);
        return null;
    }

    public void updatePatientsTableCurrentPage(Integer pageNumber) {
        baseModel.setPatientsTableCurrentPage(pageNumber);
    }

    public void updateDoctorsTableCurrentPage(Integer pageNumber) {
        baseModel.setDoctorsTableCurrentPage(pageNumber);
    }

    public void handleDoctorSpecialtyComboBoxCheckedItemsChanged(ObservableList<?> itemList) {
        baseModel.setDoctorSpecialtyComboBoxCheckedItemsNumber(itemList.size());
    }

    public void invalidateSession() {
        sessionStore.setToken(null);
        sessionStore.setUserInfo(null);
    }

    private void setPatientsTablePageDetails(Map<String, String> headers) {
        Integer currentPage = Integer.valueOf(headers.getOrDefault(CURRENT_PAGE, "1"));
        Integer totalPages = Integer.valueOf(headers.getOrDefault(TOTAL_PAGES, "1"));
        baseModel.setPatientsTableCurrentPage(currentPage);
        baseModel.setPatientsTableTotalPages(totalPages);
    }

    private void setDoctorsTablePageDetails(Map<String, String> headers) {
        Integer currentPage = Integer.valueOf(headers.getOrDefault(CURRENT_PAGE, "1"));
        Integer totalPages = Integer.valueOf(headers.getOrDefault(TOTAL_PAGES, "1"));
        baseModel.setDoctorsTableCurrentPage(currentPage);
        baseModel.setDoctorsTableTotalPages(totalPages);
    }

    private List<PersonTableRecord> createPersonTableContent(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<PersonPagedResponse>>(){}.getType();
        List<PersonPagedResponse> recordList = new Gson().fromJson(jsonArray, listType);
        return mapPersonPagedResponseToBaseRecord(recordList);
    }

    private List<PersonTableRecord> mapPersonPagedResponseToBaseRecord(List<PersonPagedResponse> response) {
        return response
                .stream()
                .map(e -> new PersonTableRecord(
                        e.getId(),
                        new SimpleStringProperty(e.getFirstName()),
                        new SimpleStringProperty(e.getLastName())))
                .collect(Collectors.toList());
    }

}
