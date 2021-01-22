package pl.motokomando.healthcare.domain.patients;

import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.PatientHealthInfo;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;

public interface PatientsService {

    PatientBasicPage getAllPatients(BasicPagedQueryCommand command);
    Patient getPatient(Integer id);
    void savePatient(PatientRequestCommand command);
    PatientHealthInfo getHealthInfo(Integer id);

}
