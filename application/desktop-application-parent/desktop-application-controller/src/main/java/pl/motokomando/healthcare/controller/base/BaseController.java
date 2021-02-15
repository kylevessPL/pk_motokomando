package pl.motokomando.healthcare.controller.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.GetClient;
import pl.motokomando.healthcare.controller.utils.LocalDateAdapter;
import pl.motokomando.healthcare.controller.utils.PutClient;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.controller.utils.WebResponseUtils;
import pl.motokomando.healthcare.model.SessionStore;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.base.utils.AddDoctorDetails;
import pl.motokomando.healthcare.model.base.utils.AddPatientDetails;
import pl.motokomando.healthcare.model.base.utils.BasePagedResponse;
import pl.motokomando.healthcare.model.base.utils.BaseTableRecord;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
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

    public Void handleAddDoctorButtonClicked(AddDoctorDetails doctorDetails) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(doctorDetails);
        sendAddPersonRequest(DOCTORS, body);
        return null;
    }

    public Void handleAddPatientButtonClicked(AddPatientDetails patientDetails) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String body = gson.toJson(patientDetails);
        sendAddPersonRequest(PATIENTS, body);
        return null;
    }

    public Void updateDoctorsTablePageData() throws Exception {
        int page = baseModel.getDoctorsTableCurrentPage();
        int size = baseModel.getTableCountPerPage();
        HttpResponse response = getBaseTableDataResponse(DOCTORS, page, size);
        Map<String, String> headers = WebResponseUtils.extractPageHeaders(response);
        setDoctorsTablePageDetails(headers);
        String responseBody = EntityUtils.toString(response.getEntity());
        List<BaseTableRecord> tableContent = createBaseTableContent(responseBody);
        baseModel.setDoctorsTablePageContent(tableContent);
        return null;
    }

    public Void updatePatientsTablePageData() throws Exception {
        int page = baseModel.getPatientsTableCurrentPage();
        int size = baseModel.getTableCountPerPage();
        HttpResponse response = getBaseTableDataResponse(PATIENTS, page, size);
        Map<String, String> headers = WebResponseUtils.extractPageHeaders(response);
        setPatientsTablePageDetails(headers);
        String responseBody = EntityUtils.toString(response.getEntity());
        List<BaseTableRecord> tableContent = createBaseTableContent(responseBody);
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

    private void sendAddPersonRequest(String path, String body) throws Exception {
        WebClient client = PutClient.builder()
                .path(path)
                .body(body)
                .build();
        HttpResponse response = client.execute();
        if (response.getStatusLine().getStatusCode() != SC_NO_CONTENT) {
            WebResponseUtils.mapErrorResponseAsException(response);
        }
    }

    private HttpResponse getBaseTableDataResponse(String path, int page, int size) throws Exception {
        WebClient client = GetClient.builder()
                .path(path)
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

    private List<BaseTableRecord> createBaseTableContent(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<BasePagedResponse>>(){}.getType();
        List<BasePagedResponse> recordList = new Gson().fromJson(jsonArray, listType);
        return WebResponseUtils.mapBasePagedResponseToBaseRecord(recordList);
    }

}
