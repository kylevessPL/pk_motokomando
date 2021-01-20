package pl.motokomando.healthcare.domain.prescriptions.medicines;

import pl.motokomando.healthcare.domain.model.prescriptions.medicines.PrescriptionMedicine;
import pl.motokomando.healthcare.domain.model.utils.Basic;

import java.util.Optional;

public interface PrescriptionMedicinesRepository {

    Optional<PrescriptionMedicine> getPrescriptionMedicine(Integer prescriptionId, Integer prescriptionMedicineId);
    Basic createPrescriptionMedicine(Integer prescriptionId, String productNDC);
    boolean deletePrescriptionMedicine(Integer prescriptionId, Integer prescriptionMedicineId);
    boolean prescriptionMedicineExists(Integer prescriptionId, String productNDC);

}
