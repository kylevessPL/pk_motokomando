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
    public PatientBasicPage getAllPatients(BasicQueryCommand command) {
        PageProperties pageProperties = new PageProperties(command.getPage(), command.getSize());
        SortProperties sortProperties = new SortProperties(command.getSortBy(), command.getSortDir());
        return patientsRepository.getAllPatients(pageProperties, sortProperties);
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatient(Integer id) {
        PatientDetails patientDetails = patientsRepository.getPatientById(id)
                .orElseThrow(() -> new MyException(PATIENT_NOT_FOUND));
        PatientBasicInfo basicInfo = patientRecordsRepository.getPatientRecordBasicByPatientId(id);
        return new Patient(basicInfo, patientDetails);
    }

    @Override
    @Transactional
    public void savePatient(PatientRequestCommand command) {
        Optional<Integer> patientId = Optional.ofNullable(command.getId());
        patientId.ifPresent(this::checkPatientExistence);
        Integer id = patientsRepository.savePatient(command);
        if (!patientId.isPresent()) {
            patientRecordsRepository.createPatientRecord(id);
        }
    }

    private void checkPatientExistence(Integer id) {
        if (!patientsRepository.patientExists(id)) {
            throw new MyException(PATIENT_NOT_FOUND);
        }
    }

}
