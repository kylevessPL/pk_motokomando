package pl.motokomando.healthcare.model.patient;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.base.utils.PatientDetails;
import pl.motokomando.healthcare.model.patient.utils.PatientAppointmentsTableRecord;
import pl.motokomando.healthcare.model.utils.DoctorBasic;

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
    private final SimpleObjectProperty<PatientDetails> patientDetails = new SimpleObjectProperty<>();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final IntegerProperty patientAppointmentsTableTotalPages = new SimpleIntegerProperty(1);
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<PatientAppointmentsTableRecord> patientAppointmentsTablePageContent =
            FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    private List<DoctorBasic> doctorBasicList;
    private LocalDateTime patientRegistrationDate;
    private Integer patientAppointmentsTableCurrentPage = 1;

    @Setter(NONE)
    private Integer tableCountPerPage = 14;

    public PatientDetails getPatientDetails() {
        return patientDetails.get();
    }

    public void setPatientDetails(PatientDetails patientDetails) {
        this.patientDetails.set(patientDetails);
    }

    public void setPatientAppointmentsTableTotalPages(Integer value) {
        patientAppointmentsTableTotalPages.set(value);
    }

    public void setPatientAppointmentsTablePageContent(List<PatientAppointmentsTableRecord> tablePageContent) {
        patientAppointmentsTablePageContent.setAll(tablePageContent);
    }

}
