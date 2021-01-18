package pl.motokomando.healthcare.api.prescriptions.medicines.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineRequest {

    @Schema(description = "Medicine NDC", example = "0536-1261")
    @NotBlank(message = "Product NDC is mandatory")
    private String productNDC;

}
