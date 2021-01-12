package pl.motokomando.healthcare.domain.model.prescriptions.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineDeleteRequestCommand {

    private Integer prescriptionId;
    private String productNDC;

}
