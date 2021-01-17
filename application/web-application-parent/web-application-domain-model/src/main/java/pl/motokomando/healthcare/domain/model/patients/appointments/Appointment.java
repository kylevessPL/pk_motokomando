package pl.motokomando.healthcare.domain.model.patients.appointments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class Appointment {

    private final Integer id;
    private final LocalDateTime scheduleDate;
    private final LocalDateTime appointmentDate;
    private final Integer billId;
    private final Integer doctorId;
    private final Integer prescriptionId;
    private final String diagnosis;
    private final AppointmentStatus appointmentStatus;

}
