package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionMedicinesEntity;

public interface PrescriptionMedicinesEntityDao extends JpaRepository<PrescriptionMedicinesEntity, Integer> {

    long deleteByPrescriptionIdAndProductNDC(Integer prescriptionId, String productNDC);
    boolean existsByPrescriptionIdAndProductNDC(Integer prescriptionId, String productNDC);
}
