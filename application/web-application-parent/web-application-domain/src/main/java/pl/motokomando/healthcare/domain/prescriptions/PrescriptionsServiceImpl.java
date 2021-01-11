package pl.motokomando.healthcare.domain.prescriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionBasic;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.MyException;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.PRESCRIPTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PrescriptionsServiceImpl implements PrescriptionsService {

    private final PrescriptionsRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Prescription getPrescriptionById(Integer id) {
        return repository.getPrescriptionById(id)
                .orElseThrow(() -> new MyException(PRESCRIPTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public PrescriptionBasic createPrescription(PrescriptionRequestCommand command) {
        return repository.createPrescription(command);
    }

    @Override
    @Transactional
    public void updatePrescription(PrescriptionPatchRequestCommand command) {
        repository.updatePrescription(command);
    }

}
