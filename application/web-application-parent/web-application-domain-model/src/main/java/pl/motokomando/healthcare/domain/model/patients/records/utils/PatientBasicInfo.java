package pl.motokomando.healthcare.domain.model.patients.records.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class PatientBasicInfo {

    private final Integer patientId;
    private final LocalDateTime registrationDate;

}
