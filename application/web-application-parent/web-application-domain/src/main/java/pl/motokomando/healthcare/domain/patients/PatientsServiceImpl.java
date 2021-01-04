package pl.motokomando.healthcare.domain.patients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasic;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientQueryCommand;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientsServiceImpl implements PatientsService {

    private final PatientsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PatientBasic> getAllPatients(PatientQueryCommand query) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatientById(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public void savePatient(PatientRequestCommand request) {

    }

}
