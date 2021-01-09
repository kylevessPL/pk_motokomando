package pl.motokomando.healthcare.domain.patientrecords;

import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientBasicInfo;

public interface PatientRecordsRepository {

    PatientBasicInfo getPatientRecordBasicByPatientId(Integer patientId);
    void create(Integer patientId);

}
