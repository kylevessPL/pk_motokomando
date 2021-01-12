package pl.motokomando.healthcare.domain.prescriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.medicines.MedicinesService;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionBasic;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;
import pl.motokomando.healthcare.domain.model.utils.NoMedicinesFoundException;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_MEDICINE_ALREADY_EXISTS;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_MEDICINE_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PrescriptionsServiceImpl implements PrescriptionsService {

    private final MedicinesService medicinesService;
    private final PrescriptionsRepository prescriptionsRepository;
    private final PrescriptionMedicinesRepository prescriptionMedicinesRepository;

    @Override
    @Transactional(readOnly = true)
    public Prescription getPrescriptionById(Integer id) {
        return prescriptionsRepository.getPrescriptionById(id)
                .orElseThrow(() -> new MyException(PRESCRIPTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public PrescriptionBasic createPrescription(PrescriptionRequestCommand command) {
        return prescriptionsRepository.createPrescription(command);
    }

    @Override
    @Transactional
    public void updatePrescription(PrescriptionPatchRequestCommand command) {
        prescriptionsRepository.updatePrescription(command);
    }

    @Override
    @Transactional
    public void deletePrescription(Integer id) {
        boolean deleteResult = prescriptionsRepository.deletePrescription(id);
        if (!deleteResult) {
            throw new MyException(PRESCRIPTION_NOT_FOUND);
        }
    }

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
            medicinesService.getMedicineByProductNDC(productNDC);
        } catch (NoMedicinesFoundException ex) {
            throw new MyException(ex.getErrorCode());
        }
    }

}
