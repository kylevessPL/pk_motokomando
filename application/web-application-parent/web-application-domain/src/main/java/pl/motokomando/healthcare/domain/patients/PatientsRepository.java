package pl.motokomando.healthcare.domain.patients;

import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;

import java.util.Optional;

public interface PatientsRepository {

    PatientBasicPage getAllPatients(PageProperties pageProperties, SortProperties sortProperties);
    Optional<PatientDetails> getPatientById(Integer id);
    boolean patientExists(Integer id);
    Integer savePatient(PatientRequestCommand data);

}
