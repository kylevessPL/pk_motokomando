package pl.motokomando.healthcare.domain.model.patients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails;

@RequiredArgsConstructor
@Getter
public final class Patient {

    private final PatientBasicInfo basicInfo;
    private final PatientDetails patientDetails;

}
