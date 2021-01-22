package pl.motokomando.healthcare.domain.patients.records;

import pl.motokomando.healthcare.domain.model.patients.records.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientRecordPatchRequestCommand;

public interface PatientRecordsService {

    PatientRecord getPatientRecord(Integer patientId);
    void updatePatientRecord(PatientRecordPatchRequestCommand command);

}
