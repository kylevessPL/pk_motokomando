package pl.motokomando.healthcare.domain.prescriptions.medicines;

import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineRequestCommand;

public interface PrescriptionMedicinesService {

    void createPrescriptionMedicine(Integer prescriptionId, PrescriptionMedicineRequestCommand command);
    void deletePrescriptionMedicine(PrescriptionMedicineDeleteRequestCommand command);

}
