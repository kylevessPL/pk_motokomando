package pl.motokomando.healthcare.infrastructure.prescriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionBasic;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.prescriptions.PrescriptionsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PrescriptionsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.PrescriptionsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionsEntity;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrescriptionsRepositoryImpl implements PrescriptionsRepository {

    private final PrescriptionsEntityDao dao;
    private final PrescriptionsEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Prescription> getPrescriptionById(Integer id) {
        return mapper.mapToPrescription(dao.findById(id));
    }

    @Override
    @Transactional
    public void updatePrescription(PrescriptionPatchRequestCommand data) {
        PrescriptionsEntity prescriptionsEntity = createEntity(data);
        dao.save(prescriptionsEntity);
    }

    @Override
    @Transactional
    public PrescriptionBasic createPrescription(PrescriptionRequestCommand data) {
        PrescriptionsEntity prescriptionsEntity = createEntity(data);
        Integer id = dao.save(prescriptionsEntity).getId();
        return mapper.mapToPrescriptionBasic(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean prescriptionExists(Integer id) {
        return dao.existsById(id);
    }

    private PrescriptionsEntity createEntity(PrescriptionRequestCommand data) {
        PrescriptionsEntity prescriptionsEntity = new PrescriptionsEntity();
        prescriptionsEntity.setExpirationDate(data.getExpirationDate());
        prescriptionsEntity.setNotes(data.getNotes());
        return prescriptionsEntity;
    }

    private PrescriptionsEntity createEntity(PrescriptionPatchRequestCommand data) {
        PrescriptionsEntity prescriptionsEntity = new PrescriptionsEntity();
        prescriptionsEntity.setId(data.getId());
        prescriptionsEntity.setIssueDate(Timestamp.valueOf(data.getIssueDate()));
        prescriptionsEntity.setExpirationDate(data.getExpirationDate());
        prescriptionsEntity.setNotes(data.getNotes());
        return prescriptionsEntity;
    }

}
