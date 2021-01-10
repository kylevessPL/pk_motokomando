package pl.motokomando.healthcare.domain.patientrecords;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PATIENT_RECORD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PatientRecordsServiceImpl implements PatientRecordsService {

    private final PatientRecordsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public PatientRecord getPatientRecordById(Integer id) {
        return repository.getPatientRecordById(id)
                .orElseThrow(() -> new MyException(PATIENT_RECORD_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updatePatientRecord(PatientRecordRequestCommand request) {
        repository.updatePatientRecord(request);
    }

}
