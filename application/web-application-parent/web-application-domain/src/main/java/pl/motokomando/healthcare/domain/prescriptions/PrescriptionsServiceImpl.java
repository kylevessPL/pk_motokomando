package pl.motokomando.healthcare.domain.prescriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;
import pl.motokomando.healthcare.domain.model.utils.BasicException;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PrescriptionsServiceImpl implements PrescriptionsService {

    private final PrescriptionsRepository prescriptionsRepository;

    @Override
    @Transactional(readOnly = true)
    public Prescription getPrescription(Integer id) {
        return prescriptionsRepository.getPrescriptionById(id)
                .orElseThrow(() -> new BasicException(PRESCRIPTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public Basic createPrescription(PrescriptionRequestCommand command) {
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
            throw new BasicException(PRESCRIPTION_NOT_FOUND);
        }
    }

}
