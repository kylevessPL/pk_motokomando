package pl.motokomando.healthcare.domain.bills;

import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordPatchRequestCommand;

public interface BillsService {

    PatientRecord getPatientRecordById(Integer id);
    void updatePatientRecord(PatientRecordPatchRequestCommand request);

}
