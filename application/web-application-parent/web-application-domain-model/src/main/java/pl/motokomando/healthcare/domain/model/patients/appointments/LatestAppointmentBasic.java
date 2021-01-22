package pl.motokomando.healthcare.domain.model.patients.appointments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class LatestAppointmentBasic {

    private final LocalDateTime appointmentDate;
    private final Integer doctorId;
    private final String diagnosis;

}
