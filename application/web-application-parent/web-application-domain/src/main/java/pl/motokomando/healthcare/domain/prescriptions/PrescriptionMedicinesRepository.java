package pl.motokomando.healthcare.domain.prescriptions;

public interface PrescriptionMedicinesRepository {

    void createPrescriptionMedicine(Integer prescriptionId, String productNDC);
    Long deletePrescriptionMedicine(Integer prescriptionId, String productNDC);
    boolean prescriptionMedicineExists(Integer prescriptionId, String productNDC);

}
