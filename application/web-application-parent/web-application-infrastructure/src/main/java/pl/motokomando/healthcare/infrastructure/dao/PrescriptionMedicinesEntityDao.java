package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionMedicinesEntity;

import java.util.List;
import java.util.Optional;

public interface PrescriptionMedicinesEntityDao extends JpaRepository<PrescriptionMedicinesEntity, Integer> {

    List<PrescriptionMedicinesEntity> getAllByPrescriptionId(Integer prescriptionId);
    Optional<PrescriptionMedicinesEntity> getByIdAndPrescriptionId(Integer prescriptionMedicineId, Integer prescriptionId);
    long deleteByIdAndPrescriptionId(Integer prescriptionMedicineId, Integer prescriptionId);
    boolean existsByPrescriptionIdAndProductNDC(Integer prescriptionId, String productNDC);

}
