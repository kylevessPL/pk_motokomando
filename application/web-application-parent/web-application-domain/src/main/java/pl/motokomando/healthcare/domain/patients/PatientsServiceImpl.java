package pl.motokomando.healthcare.domain.patients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;

import java.util.Optional;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PatientsServiceImpl implements PatientsService {

    private final PatientsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public PatientBasicPage getAllPatients(BasicQueryCommand query) {
        PageProperties pageProperties = new PageProperties(query.getPage(), query.getSize());
        SortProperties sortProperties = new SortProperties(query.getSortBy(), query.getSortDir());
        return repository.getAllPatients(pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatientById(Integer id) {
        return repository.getPatientById(id).orElseThrow(() -> new MyException(PATIENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public void savePatient(PatientRequestCommand request) {
        Optional.ofNullable(request.getId()).ifPresent(this::checkPatientExistence);
        repository.savePatient(request);
    }

    private void checkPatientExistence(Integer id) {
        if (!repository.patientExists(id)) {
            throw new MyException(PATIENT_NOT_FOUND);
        }
    }

}
