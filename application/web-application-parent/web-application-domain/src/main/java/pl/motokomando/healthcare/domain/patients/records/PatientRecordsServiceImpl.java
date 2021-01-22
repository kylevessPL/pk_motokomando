package pl.motokomando.healthcare.domain.patients.records;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.records.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicException;
import pl.motokomando.healthcare.domain.patients.PatientsRepository;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_RECORD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PatientRecordsServiceImpl implements PatientRecordsService {

    private final PatientRecordsRepository repository;
    private final PatientsRepository patientsRepository;

    @Override
    @Transactional(readOnly = true)
    public PatientRecord getPatientRecord(Integer patientId) {
        checkPatientExistence(patientId);
        return repository.getPatientRecordByPatientId(patientId)
                .orElseThrow(() -> new BasicException(PATIENT_RECORD_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updatePatientRecord(PatientRecordPatchRequestCommand command) {
        repository.updatePatientRecord(command);
    }

    private void checkPatientExistence(Integer patientId) {
        if (!patientsRepository.patientExists(patientId)) {
            throw new BasicException(PATIENT_NOT_FOUND);
        }
    }

}
