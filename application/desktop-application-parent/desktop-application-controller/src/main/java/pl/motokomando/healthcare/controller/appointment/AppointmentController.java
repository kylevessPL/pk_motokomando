package pl.motokomando.healthcare.controller.appointment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.controller.utils.GetClient;
import pl.motokomando.healthcare.controller.utils.WebClient;
import pl.motokomando.healthcare.controller.utils.WebUtils;
import pl.motokomando.healthcare.model.appointment.AppointmentModel;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesResponse;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.MEDICINES;

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

    private List<MedicinesTableRecord> createMedicinesTableContent(String responseBody) {
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        Type listType = new TypeToken<ArrayList<MedicinesResponse>>(){}.getType();
        List<MedicinesResponse> recordList = new Gson().fromJson(jsonArray, listType);
        return mapMedicinesResponseToMedicinesTableRecord(recordList);
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
