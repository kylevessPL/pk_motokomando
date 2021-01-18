package pl.motokomando.healthcare.domain.model.patients.appointments.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class AppointmentBasicPaged {

    private final Integer id;
    private final LocalDateTime appointmentDate;
    private final AppointmentStatus appointmentStatus;

}
