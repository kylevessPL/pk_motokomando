package pl.motokomando.healthcare.domain.patientrecords;

import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordPatchRequestCommand;

import java.util.Optional;

public interface PatientRecordsRepository {

    Optional<PatientRecord> getPatientRecordById(Integer id);
    void updatePatientRecord(PatientRecordPatchRequestCommand data);
    PatientBasicInfo getPatientRecordBasicByPatientId(Integer patientId);
    void createPatientRecord(Integer patientId);

}
