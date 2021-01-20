package pl.motokomando.healthcare.dto.prescriptions.medicines;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineResponse {

    @Schema(description = "Prescription medicine ID", example = "1")
    private Integer id;
    @Schema(description = "Prescription ID", example = "1")
    private Integer prescriptionId;
    @Schema(description = "Medicine NDC", example = "0536-1261")
    private String productNDC;

}
