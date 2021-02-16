package pl.motokomando.healthcare.model.patient.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public final class PatientAppointmentsPagedResponse {

    private final Integer id;
    private final LocalDateTime appointmentDate;
    private final AppointmentStatus appointmentStatus;

}
