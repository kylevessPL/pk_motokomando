package pl.motokomando.healthcare.domain.model.prescriptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.PrescriptionMedicine;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class PrescriptionFull {

    private final Prescription details;
    private final List<PrescriptionMedicine> medicines;

}
