package pl.motokomando.healthcare.model.patient;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.base.utils.PatientDetails;
import pl.motokomando.healthcare.model.patient.utils.PatientAppointmentsTableRecord;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.NONE;

@Getter
@Setter
@RequiredArgsConstructor
public final class PatientModel {

    private final Integer patientId;

    @Accessors(fluent = true)
    @Setter(NONE)
    private final IntegerProperty patientAppointmentsTableTotalPages = new SimpleIntegerProperty(1);
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<PatientAppointmentsTableRecord> patientAppointmentsTablePageContent =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private LocalDateTime patientRegistrationDate;
    private PatientDetails patientDetails;

    private Integer patientAppointmentsTableCurrentPage = 1;
    private Integer tableCountPerPage = 19;


    public void setPatientAppointmentsTableTotalPages(Integer value) {
        patientAppointmentsTableTotalPages.set(value);
    }

    public void setPatientAppointmentsTablePageContent(List<PatientAppointmentsTableRecord> tablePageContent) {
        patientAppointmentsTablePageContent.setAll(tablePageContent);
    }

}
