package pl.motokomando.healthcare.infrastructure.prescriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.prescriptions.PrescriptionMedicinesRepository;
import pl.motokomando.healthcare.infrastructure.dao.PrescriptionMedicinesEntityDao;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionMedicinesEntity;

@Service
@RequiredArgsConstructor
public class PrescriptionMedicinesRepositoryImpl implements PrescriptionMedicinesRepository {

    private final PrescriptionMedicinesEntityDao dao;

    @Override
    @Transactional
    public void createPrescriptionMedicine(Integer prescriptionId, String productNDC) {
        PrescriptionMedicinesEntity prescriptionMedicinesEntity = createEntity(prescriptionId, productNDC);
        dao.save(prescriptionMedicinesEntity);
    }

    @Override
    @Transactional
    public boolean deletePrescriptionMedicine(Integer prescriptionId, String productNDC) {
        long result = dao.deleteByPrescriptionIdAndProductNDC(prescriptionId, productNDC);
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
