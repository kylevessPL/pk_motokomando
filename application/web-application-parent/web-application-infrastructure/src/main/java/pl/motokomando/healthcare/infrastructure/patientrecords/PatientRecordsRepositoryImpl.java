package pl.motokomando.healthcare.infrastructure.patientrecords;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.patientrecords.PatientRecordsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PatientRecordsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.PatientRecordsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

@Service
@RequiredArgsConstructor
public class PatientRecordsRepositoryImpl implements PatientRecordsRepository {

    private final PatientRecordsEntityDao dao;
    private final PatientRecordsEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PatientBasicInfo getPatientRecordBasicByPatientId(Integer patientId) {
        return mapper.mapToPatientBasicInfo(dao.getByPatientId(patientId));
    }

    @Override
    @Transactional
    public void create(Integer patientId) {
        PatientRecordsEntity patientRecordsEntity = createEntity(patientId);
        dao.save(patientRecordsEntity);
    }

    private PatientRecordsEntity createEntity(Integer patientId) {
        PatientRecordsEntity patientRecordsEntity = new PatientRecordsEntity();
        patientRecordsEntity.setPatientId(patientId);
        return patientRecordsEntity;
    }

}
