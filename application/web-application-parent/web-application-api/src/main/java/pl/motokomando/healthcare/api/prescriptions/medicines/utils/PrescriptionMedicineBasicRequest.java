package pl.motokomando.healthcare.api.prescriptions.medicines.utils;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineBasicRequest {

    @Parameter(description = "Prescription ID", example = "1")
    @NotNull(message = "Prescription ID is mandatory")
    @Min(value = 1, message = "Prescription ID must be a positive integer value")
    private Integer prescriptionId;
    @Parameter(description = "Prescription medicine ID", example = "3")
    @NotNull(message = "Prescription medicine ID is mandatory")
    @Min(value = 1, message = "Prescription medicine ID must be a positive integer value")
    private Integer prescriptionMedicineId;

}
