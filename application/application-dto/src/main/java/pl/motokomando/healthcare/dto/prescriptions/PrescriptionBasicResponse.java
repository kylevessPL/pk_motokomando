package pl.motokomando.healthcare.dto.prescriptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionBasicResponse {

    @Schema(description = "Prescription ID", example = "1")
    private Integer id;

}
