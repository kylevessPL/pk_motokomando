package pl.motokomando.healthcare.controller.base;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import org.apache.http.HttpResponse;
import pl.motokomando.healthcare.controller.utils.PutClient;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.model.SessionStore;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.base.utils.AddDoctorDetails;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.UPDATE_DOCTOR;

public class BaseController {

    private final BaseModel baseModel;

    private final SessionStore sessionStore = SessionStore.getInstance();

    public BaseController(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    //TODO nie tu
    /*public TableRow<?> nazwa() {
        TableRow<DoctorRecord> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                DoctorRecord rowData = row.getItem();
                System.out.println("działa");
            }
        });
        return row;
    }*/

    public void handleDoctorSpecialtyComboBoxCheckedItemsChanged(ObservableList<?> itemList) {
        Integer checkedItemsNumber = itemList.size();
        baseModel.setDoctorSpecialtyComboBoxCheckedItemsNumber(checkedItemsNumber);
    }

    public Void handleAddDoctorButtonClicked(AddDoctorDetails doctorDetails) throws Exception {
        Gson gson = new Gson();
        String body = gson.toJson(doctorDetails);
        WebClient client = PutClient.builder()
                .path(UPDATE_DOCTOR)
                .body(body)
                .build();
        HttpResponse response = client.execute();
        if (response.getStatusLine().getStatusCode() != SC_NO_CONTENT) {
            client.mapErrorResponseAsException(response);
        }
        return null;
    }

    public void invalidateSession() {
        sessionStore.setToken(null);
        sessionStore.setUserInfo(null);
    }

}
