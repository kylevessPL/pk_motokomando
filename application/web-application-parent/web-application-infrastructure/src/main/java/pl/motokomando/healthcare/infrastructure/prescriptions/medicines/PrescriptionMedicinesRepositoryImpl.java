package pl.motokomando.healthcare.infrastructure.prescriptions.medicines;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.PrescriptionMedicine;
import pl.motokomando.healthcare.domain.model.utils.Basic;
import pl.motokomando.healthcare.domain.prescriptions.medicines.PrescriptionMedicinesRepository;
import pl.motokomando.healthcare.infrastructure.dao.PrescriptionMedicinesEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.BasicEntityMapper;
import pl.motokomando.healthcare.infrastructure.mapper.PrescriptionMedicinesEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionMedicinesEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrescriptionMedicinesRepositoryImpl implements PrescriptionMedicinesRepository {

    private final PrescriptionMedicinesEntityDao dao;
    private final PrescriptionMedicinesEntityMapper prescriptionMedicinesEntityMapper;
    private final BasicEntityMapper basicEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<PrescriptionMedicine> getPrescriptionMedicine(Integer prescriptionId, Integer prescriptionMedicineId) {
        return prescriptionMedicinesEntityMapper.mapToPrescriptionMedicine(dao.getByIdAndPrescriptionId(prescriptionMedicineId, prescriptionId));
    }

    @Override
    @Transactional
    public Basic createPrescriptionMedicine(Integer prescriptionId, String productNDC) {
        PrescriptionMedicinesEntity prescriptionMedicinesEntity = createEntity(prescriptionId, productNDC);
        Integer id = dao.save(prescriptionMedicinesEntity).getId();
        return basicEntityMapper.mapToBasic(id);
    }

    @Override
    @Transactional
    public boolean deletePrescriptionMedicine(Integer prescriptionId, Integer prescriptionMedicineId) {
        long result = dao.deleteByIdAndPrescriptionId(prescriptionMedicineId, prescriptionId);
        return result != 0;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean prescriptionMedicineExists(Integer prescriptionId, String productNDC) {
        return dao.existsByPrescriptionIdAndProductNDC(prescriptionId, productNDC);
    }

    private PrescriptionMedicinesEntity createEntity(Integer prescriptionId, String productNDC) {
        PrescriptionMedicinesEntity prescriptionMedicinesEntity = new PrescriptionMedicinesEntity();
        prescriptionMedicinesEntity.setPrescriptionId(prescriptionId);
        prescriptionMedicinesEntity.setProductNDC(productNDC);
        return prescriptionMedicinesEntity;
    }

}
