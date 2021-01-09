package pl.motokomando.healthcare.domain.patients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;
import pl.motokomando.healthcare.domain.patientrecords.PatientRecordsRepository;

import java.util.Optional;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PatientsServiceImpl implements PatientsService {

    private final PatientsRepository patientsRepository;
    private final PatientRecordsRepository patientRecordsRepository;

    @Override
    @Transactional(readOnly = true)
    public PatientBasicPage getAllPatients(BasicQueryCommand query) {
        PageProperties pageProperties = new PageProperties(query.getPage(), query.getSize());
        SortProperties sortProperties = new SortProperties(query.getSortBy(), query.getSortDir());
        return patientsRepository.getAllPatients(pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatientById(Integer id) {
        PatientDetails patientDetails = patientsRepository.getPatientById(id)
                .orElseThrow(() -> new MyException(PATIENT_NOT_FOUND));
        PatientBasicInfo basicInfo = patientRecordsRepository.getPatientRecordBasicByPatientId(id);
        return new Patient(basicInfo, patientDetails);
    }

    @Override
    @Transactional
    public void savePatient(PatientRequestCommand request) {
        Optional<Integer> patientId = Optional.ofNullable(request.getId());
        patientId.ifPresent(this::checkPatientExistence);
        Integer id = patientsRepository.savePatient(request);
        if (!patientId.isPresent()) {
            patientRecordsRepository.create(id);
        }
    }

    private void checkPatientExistence(Integer id) {
        if (!patientsRepository.patientExists(id)) {
            throw new MyException(PATIENT_NOT_FOUND);
        }
    }

}
