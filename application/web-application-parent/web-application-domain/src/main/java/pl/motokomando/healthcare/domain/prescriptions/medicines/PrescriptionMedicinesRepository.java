package pl.motokomando.healthcare.domain.prescriptions.medicines;

public interface PrescriptionMedicinesRepository {

    void createPrescriptionMedicine(Integer prescriptionId, String productNDC);
    boolean deletePrescriptionMedicine(Integer prescriptionId, String productNDC);
    boolean prescriptionMedicineExists(Integer prescriptionId, String productNDC);

}