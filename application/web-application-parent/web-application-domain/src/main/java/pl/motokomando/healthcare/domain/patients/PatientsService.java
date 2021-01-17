package pl.motokomando.healthcare.domain.patients;

import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;

public interface PatientsService {

    PatientBasicPage getAllPatients(BasicQueryCommand command);
    Patient getPatient(Integer id);
    void savePatient(PatientRequestCommand command);

}
