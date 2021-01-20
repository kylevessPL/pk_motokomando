package pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineBasicRequestCommand {

    private Integer prescriptionId;
    private Integer prescriptionMedicineId;

}
