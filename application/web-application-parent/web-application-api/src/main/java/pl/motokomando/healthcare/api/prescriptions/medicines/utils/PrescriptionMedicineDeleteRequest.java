package pl.motokomando.healthcare.api.prescriptions.medicines.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineDeleteRequest {

    @Schema(description = "Prescription ID", example = "56")
    @NotNull(message = "Prescription ID is mandatory")
    @Min(value = 1, message = "Prescription ID must be a positive integer value")
    private Integer id;
    @Schema(description = "Medicine NDC", example = "0536-1261")
    @NotBlank(message = "Product NDC is mandatory")
    private String productNDC;

}
