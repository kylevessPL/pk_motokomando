package pl.motokomando.healthcare.model.patient.utils;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
public final class PatientAppointmentsTableRecord {

    private final Integer id;
    @Accessors(fluent = true)
    private final SimpleStringProperty appointmentDate;
    @Accessors(fluent = true)
    private final SimpleStringProperty appointmentStatus;

}
