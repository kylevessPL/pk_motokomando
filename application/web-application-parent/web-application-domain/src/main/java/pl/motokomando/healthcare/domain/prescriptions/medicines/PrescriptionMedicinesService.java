package pl.motokomando.healthcare.domain.prescriptions.medicines;

import pl.motokomando.healthcare.domain.model.prescriptions.medicines.PrescriptionMedicine;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineBasicRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;

public interface PrescriptionMedicinesService {

    PrescriptionMedicine getPrescriptionMedicine(PrescriptionMedicineBasicRequestCommand command);
    Basic createPrescriptionMedicine(Integer prescriptionId, PrescriptionMedicineRequestCommand command);
    void deletePrescriptionMedicine(PrescriptionMedicineBasicRequestCommand command);

}
