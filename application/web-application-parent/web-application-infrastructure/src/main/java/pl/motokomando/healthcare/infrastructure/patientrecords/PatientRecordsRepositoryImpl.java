package pl.motokomando.healthcare.infrastructure.patientrecords;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.domain.patientrecords.PatientRecordsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PatientRecordsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.PatientRecordsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientRecordsRepositoryImpl implements PatientRecordsRepository {

    private final PatientRecordsEntityDao dao;
    private final PatientRecordsEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientRecord> getPatientRecordById(Integer id) {
        return mapper.mapToPatientRecord(dao.findById(id));
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
        return mapper.mapToPatientBasicInfo(dao.getByPatientId(patientId));
    }

    @Override
    @Transactional
    public void createPatientRecord(Integer patientId) {
        PatientRecordsEntity patientRecordsEntity = createEntity(patientId);
        dao.save(patientRecordsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean patientRecordExists(Integer id) {
        return dao.existsById(id);
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
