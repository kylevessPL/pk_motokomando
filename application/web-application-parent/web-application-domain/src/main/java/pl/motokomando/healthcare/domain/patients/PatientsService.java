package pl.motokomando.healthcare.domain.patients;

import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientQueryCommand;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;

public interface PatientsService {

    PatientBasicPage getAllPatients(PatientQueryCommand query);
    Patient getPatientById(Integer id);
    void savePatient(PatientRequestCommand request);

}
