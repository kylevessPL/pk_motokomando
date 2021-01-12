package pl.motokomando.healthcare.domain.prescriptions;

import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionBasic;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;

import java.util.Optional;

public interface PrescriptionsRepository {

    Optional<Prescription> getPrescriptionById(Integer id);
    void updatePrescription(PrescriptionPatchRequestCommand data);
    PrescriptionBasic createPrescription(PrescriptionRequestCommand data);
    boolean deletePrescription(Integer id);
    boolean prescriptionExists(Integer id);

}
