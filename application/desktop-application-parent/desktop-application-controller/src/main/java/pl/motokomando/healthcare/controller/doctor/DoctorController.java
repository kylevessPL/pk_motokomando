package pl.motokomando.healthcare.controller.doctor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.collections.ObservableList;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.GetClient;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.controller.utils.WebUtils;
import pl.motokomando.healthcare.model.base.utils.DoctorDetails;
import pl.motokomando.healthcare.model.doctor.DoctorModel;

import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTOR;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.DOCTORS;

public class DoctorController {

    private final DoctorModel doctorModel;

    public DoctorController(DoctorModel doctorModel) {
        this.doctorModel = doctorModel;
    }

    public Void getDoctorDetails() throws Exception {
        HttpResponse response = sendGetDoctorDetailsRequest();
        extractDoctorDetails(EntityUtils.toString(response.getEntity()));
        return null;
    }

    public Void handleUpdateDoctorDetailsButtonClicked(DoctorDetails doctorDetails) throws Exception {
        Gson gson = new Gson();
        JsonObject jsonObject = (JsonObject) gson.toJsonTree(doctorDetails);
        jsonObject.addProperty("id", doctorModel.getDoctorId());
        String body = gson.toJson(jsonObject);
        WebUtils.sendUpdatePersonRequest(DOCTORS, body);
        doctorModel.setDoctorDetails(doctorDetails);
        return null;
    }

    public void handleDoctorSpecialtyComboBoxCheckedItemsChanged(ObservableList<?> itemList) {
        doctorModel.setDoctorSpecialtyComboBoxCheckedItemsNumber(itemList.size());
    }

    private HttpResponse sendGetDoctorDetailsRequest() throws Exception {
        WebClient client = GetClient.builder()
                .path(DOCTOR)
                .pathVariable("id", String.valueOf(doctorModel.getDoctorId()))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

    private void extractDoctorDetails(String responseBody) {
        DoctorDetails doctorDetails = new Gson().fromJson(responseBody, DoctorDetails.class);
        doctorModel.setDoctorDetails(doctorDetails);
    }

}
