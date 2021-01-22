package pl.motokomando.healthcare.domain.model.patients.records;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.patients.records.utils.HealthStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class PatientRecord {

    private final Integer id;
    private final Integer patientId;
    private final HealthStatus healthStatus;
    private final String notes;
    private final LocalDateTime registrationDate;

}
