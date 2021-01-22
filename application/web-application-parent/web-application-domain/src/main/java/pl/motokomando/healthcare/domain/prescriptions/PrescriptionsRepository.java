package pl.motokomando.healthcare.domain.prescriptions;

import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;

import java.util.Optional;

public interface PrescriptionsRepository {

    Prescription getFullPrescriptionById(Integer id);
    Optional<Prescription> getPrescriptionById(Integer id);
    void updatePrescription(PrescriptionPatchRequestCommand data);
    Basic createPrescription(PrescriptionRequestCommand data);
    boolean deletePrescription(Integer id);
    boolean prescriptionExists(Integer id);

}
