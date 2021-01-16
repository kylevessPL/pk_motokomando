package pl.motokomando.healthcare.domain.patients;

import pl.motokomando.healthcare.domain.model.patients.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRecordPatchRequestCommand;

import java.util.Optional;

public interface PatientRecordsRepository {

    Optional<PatientRecord> getPatientRecordById(Integer id);
    void updatePatientRecord(PatientRecordPatchRequestCommand data);
    PatientBasicInfo getPatientRecordBasicByPatientId(Integer patientId);
    void createPatientRecord(Integer patientId);
    boolean patientRecordExists(Integer id);

}
