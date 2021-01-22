package pl.motokomando.healthcare.dto.prescriptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.prescriptions.medicines.PrescriptionMedicineResponse;

import java.util.List;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionFullResponse {

    @Schema(description = "Prescription basic details")
    private PrescriptionResponse details;
    @Schema(description = "Prescription medicines")
    private List<PrescriptionMedicineResponse> medicines;

}
