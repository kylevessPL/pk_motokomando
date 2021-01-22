package pl.motokomando.healthcare.dto.patients.records;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientBasicInfoResponse {

    @Schema(description = "Patient ID", example = "1")
    private Integer patientId;
    @Schema(description = "Patient registration date")
    private LocalDateTime registrationDate;

}
