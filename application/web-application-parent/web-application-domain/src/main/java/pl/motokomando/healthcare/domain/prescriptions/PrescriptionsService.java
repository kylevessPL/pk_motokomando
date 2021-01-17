package pl.motokomando.healthcare.domain.prescriptions;

import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionBasic;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;

public interface PrescriptionsService {

    Prescription getPrescriptionById(Integer id);
    PrescriptionBasic createPrescription(PrescriptionRequestCommand command);
    void updatePrescription(PrescriptionPatchRequestCommand command);
    void deletePrescription(Integer id);

}
