package pl.motokomando.healthcare.domain.patients;

import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasic;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientQueryCommand;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;

import java.util.List;

public interface PatientsService {

    List<PatientBasic> getAllPatients(PatientQueryCommand query);
    Patient getPatientById(Integer id);
    void savePatient(PatientRequestCommand request);

}
