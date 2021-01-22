package pl.motokomando.healthcare.domain.model.patients.appointments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class LatestAppointment {

    private final LocalDateTime appointmentDate;
    private final String doctorFullName;
    private final String diagnosis;

}
