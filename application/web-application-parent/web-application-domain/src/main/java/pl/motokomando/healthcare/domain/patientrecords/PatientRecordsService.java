package pl.motokomando.healthcare.domain.patientrecords;

import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordPatchRequestCommand;

public interface PatientRecordsService {

    PatientRecord getPatientRecord(Integer id);
    void updatePatientRecord(PatientRecordPatchRequestCommand command);

}
