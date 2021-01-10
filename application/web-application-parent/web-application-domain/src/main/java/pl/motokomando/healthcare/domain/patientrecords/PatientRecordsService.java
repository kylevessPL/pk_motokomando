package pl.motokomando.healthcare.domain.patientrecords;

import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordRequestCommand;

public interface PatientRecordsService {

    PatientRecord getPatientRecordById(Integer id);
    void updatePatientRecord(PatientRecordRequestCommand request);

}
