package pl.motokomando.healthcare.model.appointment;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;
import pl.motokomando.healthcare.model.appointment.utils.PrescriptionMedicinesTableRecord;
import pl.motokomando.healthcare.model.utils.DoctorBasic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.NONE;

@Getter
@Setter
@RequiredArgsConstructor
public final class AppointmentModel {

    private final Integer patientId;
    private final Integer appointmentId;

    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<MedicinesTableRecord> medicinesTableContent = FXCollections.observableArrayList();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<PrescriptionMedicinesTableRecord> prescriptionMedicinesTableContent = FXCollections.observableArrayList();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final SimpleObjectProperty<DoctorBasic> doctorDetails = new SimpleObjectProperty<>();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final SimpleStringProperty prescriptionNotes = new SimpleStringProperty();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final SimpleStringProperty billAmount = new SimpleStringProperty("0");
    @Accessors(fluent = true)
    private final SimpleObjectProperty<LocalDateTime> billIssueDate = new SimpleObjectProperty<>();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final SimpleObjectProperty<LocalDate> prescriptionExpirationDate = new SimpleObjectProperty<>();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final SimpleObjectProperty<LocalDateTime> prescriptionIssueDate = new SimpleObjectProperty<>();

    @Setter(NONE)
    private List<Integer> prescriptionMedicineList = new ArrayList<>();

    @Setter(NONE)
    private Integer tableCount = 10;

    @Nullable
    private Integer prescriptionId;
    @Nullable
    private Integer billId;

    public void setMedicinesTableContent(List<MedicinesTableRecord> tableContent) {
        medicinesTableContent.setAll(tableContent);
    }

    public void setPrescriptionMedicinesTableContent(List<PrescriptionMedicinesTableRecord> tableContent) {
        prescriptionMedicinesTableContent.setAll(tableContent);
    }

    public String getBillAmount() {
        return billAmount.get();
    }

    public String getPrescriptionNotes() {
        return prescriptionNotes.get();
    }

    public void setPrescriptionNotes(String notes) {
        prescriptionNotes.set(notes);
    }

    public void setBillAmount(String amount) {
        billAmount.set(amount);
    }

    public void setDoctorDetails(DoctorBasic doctorDetails) {
        this.doctorDetails.set(doctorDetails);
    }

    public void setBillIssueDate(LocalDateTime date) {
        billIssueDate.set(date);
    }

    public void setPrescriptionExpirationDate(LocalDate date) {
        prescriptionExpirationDate.set(date);
    }

    public void setPrescriptionIssueDate(LocalDateTime date) {
        prescriptionIssueDate.set(date);
    }

}
