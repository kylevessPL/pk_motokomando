package pl.motokomando.healthcare.domain.patients;

import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;

public interface PatientsService {

    PatientBasicPage getAllPatients(BasicQueryCommand query);
    Patient getPatientById(Integer id);
    void savePatient(PatientRequestCommand request);
    PatientRecord getPatientRecordById(Integer id);
    void updatePatientRecord(PatientRecordPatchRequestCommand request);

}
