package pl.motokomando.healthcare.controller.base;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.GetClient;
import pl.motokomando.healthcare.controller.utils.MappingUtils;
import pl.motokomando.healthcare.controller.utils.PutClient;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.model.SessionStore;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.base.utils.AddDoctorDetails;
import pl.motokomando.healthcare.model.base.utils.DoctorPagedResponse;
import pl.motokomando.healthcare.model.base.utils.DoctorRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTORS;

public class BaseController {

    private final BaseModel baseModel;

    private final SessionStore sessionStore = SessionStore.getInstance();

    public BaseController(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    public void handleDoctorSpecialtyComboBoxCheckedItemsChanged(ObservableList<?> itemList) {
        Integer checkedItemsNumber = itemList.size();
        baseModel.setDoctorSpecialtyComboBoxCheckedItemsNumber(checkedItemsNumber);
    }

    public Void handleAddDoctorButtonClicked(AddDoctorDetails doctorDetails) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(doctorDetails);
        WebClient client = PutClient.builder()
                .path(DOCTORS)
                .body(body)
                .build();
        HttpResponse response = client.execute();
        if (response.getStatusLine().getStatusCode() != SC_NO_CONTENT) {
            client.mapErrorResponseAsException(response);
        }
        return null;
    }

    public Void updateDoctorsTablePageData() throws Exception {
        String size = String.valueOf(baseModel.getTableCountPerPage());
        String page = String.valueOf(baseModel.getDoctorsTableCurrentPage());
        WebClient client = GetClient.builder()
                .path(DOCTORS)
                .parameter("page", page)
                .parameter("size", size)
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT && statusCode != SC_OK) {
            client.mapErrorResponseAsException(response);
        }
        Map<String, String> headers = client.extractPageHeaders(response);
        setDoctorsTablePageDetails(headers);
        String responseBody = EntityUtils.toString(response.getEntity());
        setDoctorsTableContent(responseBody);
        return null;
    }

    public void updateDoctorsTableCurrentPage(Integer pageNumber) {
        baseModel.setDoctorsTableCurrentPage(pageNumber);
    }

    public void invalidateSession() {
        sessionStore.setToken(null);
        sessionStore.setUserInfo(null);
    }

    private void setDoctorsTablePageDetails(Map<String, String> headers) {
        Integer currentPage = Integer.valueOf(headers.getOrDefault(CURRENT_PAGE, "1"));
        Integer totalPages = Integer.valueOf(headers.getOrDefault(TOTAL_PAGES, "1"));
        baseModel.setDoctorsTableCurrentPage(currentPage);
        baseModel.setDoctorsTableTotalPages(totalPages);
    }

    private void setDoctorsTableContent(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<DoctorPagedResponse>>(){}.getType();
        List<DoctorPagedResponse> doctorList = new Gson().fromJson(jsonArray, listType);
        List<DoctorRecord> tableContent = MappingUtils.mapDoctorPagedResponseToDoctorRecord(doctorList);
        baseModel.setDoctorsTablePageContent(tableContent);
    }

}
