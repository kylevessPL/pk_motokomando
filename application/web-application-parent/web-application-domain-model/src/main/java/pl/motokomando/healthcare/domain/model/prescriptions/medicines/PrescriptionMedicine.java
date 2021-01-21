package pl.motokomando.healthcare.domain.model.prescriptions.medicines;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class PrescriptionMedicine {

    private final Integer id;
    private final Integer prescriptionId;
    private final String productNDC;

}
