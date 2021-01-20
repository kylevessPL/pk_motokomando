package pl.motokomando.healthcare.domain.prescriptions;

import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;

public interface PrescriptionsService {

    Prescription getPrescription(Integer id);
    Basic createPrescription(PrescriptionRequestCommand command);
    void updatePrescription(PrescriptionPatchRequestCommand command);
    void deletePrescription(Integer id);

}
