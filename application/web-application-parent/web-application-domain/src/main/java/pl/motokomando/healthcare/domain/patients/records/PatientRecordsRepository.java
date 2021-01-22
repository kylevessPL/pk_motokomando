package pl.motokomando.healthcare.domain.patients.records;

import pl.motokomando.healthcare.domain.model.patients.records.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientRecordPatchRequestCommand;

import java.util.Optional;

public interface PatientRecordsRepository {

    Optional<PatientRecord> getPatientRecordByPatientId(Integer patientId);
    void updatePatientRecord(PatientRecordPatchRequestCommand data);
    PatientBasicInfo getPatientRecordBasicByPatientId(Integer patientId);
    void createPatientRecord(Integer patientId);

}
