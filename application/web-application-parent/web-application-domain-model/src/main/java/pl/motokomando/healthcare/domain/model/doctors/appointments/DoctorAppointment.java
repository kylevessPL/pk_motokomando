package pl.motokomando.healthcare.domain.model.doctors.appointments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.utils.AppointmentStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class DoctorAppointment {

    private final LocalDateTime appointmentDate;
    private final AppointmentStatus appointmentStatus;

}
