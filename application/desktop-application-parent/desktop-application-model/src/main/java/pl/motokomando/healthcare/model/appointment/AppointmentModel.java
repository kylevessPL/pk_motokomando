package pl.motokomando.healthcare.model.appointment;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;

import java.util.List;

import static lombok.AccessLevel.NONE;

@Getter
@Setter
@RequiredArgsConstructor
public final class AppointmentModel {

    private final Integer appointmentId;

    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<MedicinesTableRecord> medicinesTableContent = FXCollections.observableArrayList();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final ObservableList<MedicinesTableRecord> appointmentMedicinesTableContent = FXCollections.observableArrayList();
    @Accessors(fluent = true)
    @Setter(NONE)
    private final SimpleStringProperty prescriptionNotes = new SimpleStringProperty();

    @Setter(NONE)
    private Integer tableCount = 10;

    private Integer prescriptionId;
    private Integer billId;

    public void setMedicinesTableContent(List<MedicinesTableRecord> tableContent) {
        medicinesTableContent.setAll(tableContent);
    }

    public void setAppointmentMedicinesTableContent(List<MedicinesTableRecord> tableContent) {
        appointmentMedicinesTableContent.setAll(tableContent);
    }

    public void setPrescriptionNotes(String notes) {
        prescriptionNotes.set(notes);
    }

}
