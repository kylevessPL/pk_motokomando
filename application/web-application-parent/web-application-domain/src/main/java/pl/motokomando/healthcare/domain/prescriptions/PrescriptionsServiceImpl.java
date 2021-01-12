package pl.motokomando.healthcare.domain.prescriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionBasic;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_MEDICINE_ALREADY_EXISTS;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_MEDICINE_NOT_FOUND;
import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PrescriptionsServiceImpl implements PrescriptionsService {

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
    public void createPrescriptionMedicine(Integer prescriptionId, PrescriptionMedicineRequestCommand command) {
        checkPrescriptionMedicineExistence(prescriptionId, command.getProductNDC());
        prescriptionMedicinesRepository.createPrescriptionMedicine(prescriptionId, command.getProductNDC());
    }

    @Override
    @Transactional
    public void deletePrescriptionMedicine(PrescriptionMedicineDeleteRequestCommand command) {
        Long result = prescriptionMedicinesRepository.deletePrescriptionMedicine(
                command.getPrescriptionId(), command.getProductNDC());
        if (result == 0) {
            throw new MyException(PRESCRIPTION_MEDICINE_NOT_FOUND);
        }
    }

    private void checkPrescriptionMedicineExistence(Integer prescriptionId, String productNDC) {
        if (!prescriptionMedicinesRepository.prescriptionMedicineExists(prescriptionId, productNDC)) {
            throw new MyException(PRESCRIPTION_MEDICINE_ALREADY_EXISTS);
        }
    }

}
