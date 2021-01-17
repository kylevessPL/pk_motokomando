package pl.motokomando.healthcare.domain.prescriptions.medicines;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.medicines.MedicinesService;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;
import pl.motokomando.healthcare.domain.model.utils.NoMedicinesFoundException;
import pl.motokomando.healthcare.domain.prescriptions.PrescriptionsRepository;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_MEDICINE_ALREADY_EXISTS;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_MEDICINE_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PrescriptionMedicinesServiceImpl implements PrescriptionMedicinesService {

    private final MedicinesService medicinesService;
    private final PrescriptionsRepository prescriptionsRepository;
    private final PrescriptionMedicinesRepository prescriptionMedicinesRepository;

    @Override
    @Transactional
    public void createPrescriptionMedicine(Integer prescriptionId, PrescriptionMedicineRequestCommand command) {
        checkPrescriptionExistence(prescriptionId);
        checkPrescriptionMedicineExistence(prescriptionId, command.getProductNDC());
        checkMedicineExistence(command.getProductNDC());
        prescriptionMedicinesRepository.createPrescriptionMedicine(prescriptionId, command.getProductNDC());
    }

    @Override
    @Transactional
    public void deletePrescriptionMedicine(PrescriptionMedicineDeleteRequestCommand command) {
        boolean deleteResult = prescriptionMedicinesRepository.deletePrescriptionMedicine(
                command.getPrescriptionId(), command.getProductNDC());
        if (!deleteResult) {
            throw new MyException(PRESCRIPTION_MEDICINE_NOT_FOUND);
        }
    }

    private void checkPrescriptionExistence(Integer prescriptionId) {
        if (!prescriptionsRepository.prescriptionExists(prescriptionId)) {
            throw new MyException(PRESCRIPTION_NOT_FOUND);
        }
    }

    private void checkPrescriptionMedicineExistence(Integer prescriptionId, String productNDC) {
        if (prescriptionMedicinesRepository.prescriptionMedicineExists(prescriptionId, productNDC)) {
            throw new MyException(PRESCRIPTION_MEDICINE_ALREADY_EXISTS);
        }
    }

    private void checkMedicineExistence(String productNDC) {
        try {
            medicinesService.getMedicine(productNDC);
        } catch (NoMedicinesFoundException ex) {
            throw new MyException(ex.getErrorCode());
        }
    }

}
