package pl.motokomando.healthcare.infrastructure.patients.records;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.records.CurrentHealth;
import pl.motokomando.healthcare.domain.model.patients.records.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.domain.patients.records.PatientRecordsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PatientRecordsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.PatientRecordsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientRecordsRepositoryImpl implements PatientRecordsRepository {

    private final PatientRecordsEntityDao dao;
    private final PatientRecordsEntityMapper patientRecordsEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientRecord> getPatientRecordByPatientId(Integer patientId) {
        return patientRecordsEntityMapper.mapToPatientRecord(dao.findByPatientId(patientId));
    }

    @Override
    @Transactional
    public void updatePatientRecord(PatientRecordPatchRequestCommand data) {
        PatientRecordsEntity patientRecordsEntity = createEntity(data);
        dao.save(patientRecordsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientBasicInfo getPatientRecordBasicByPatientId(Integer patientId) {
        return patientRecordsEntityMapper.mapToPatientBasicInfo(dao.getByPatientId(patientId));
    }

    @Override
    @Transactional
    public void createPatientRecord(Integer patientId) {
        PatientRecordsEntity patientRecordsEntity = createEntity(patientId);
        dao.save(patientRecordsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CurrentHealth> getCurrentHealth(Integer patientId) {
        return patientRecordsEntityMapper.mapToCurrentHealth(dao.findByPatientId(patientId));
    }

    private PatientRecordsEntity createEntity(Integer patientId) {
        PatientRecordsEntity patientRecordsEntity = new PatientRecordsEntity();
        patientRecordsEntity.setPatientId(patientId);
        return patientRecordsEntity;
    }

    private PatientRecordsEntity createEntity(PatientRecordPatchRequestCommand data) {
        PatientRecordsEntity patientRecordsEntity = new PatientRecordsEntity();
        patientRecordsEntity.setId(data.getId());
        patientRecordsEntity.setPatientId(data.getPatientId());
        patientRecordsEntity.setHealthStatus(data.getHealthStatus());
        patientRecordsEntity.setNotes(data.getNotes());
        patientRecordsEntity.setRegistrationDate(Timestamp.valueOf(data.getRegistrationDate()));
        return patientRecordsEntity;
    }

}
