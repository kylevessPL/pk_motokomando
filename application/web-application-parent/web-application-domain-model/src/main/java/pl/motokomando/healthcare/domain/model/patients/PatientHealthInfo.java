package pl.motokomando.healthcare.domain.model.patients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.patients.appointments.LatestAppointment;
import pl.motokomando.healthcare.domain.model.patients.records.CurrentHealth;

@RequiredArgsConstructor
@Getter
public final class PatientHealthInfo {

    private final Integer patientId;
    private final CurrentHealth currentHealth;
    private final LatestAppointment latestAppointment;

}
