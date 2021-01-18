package pl.motokomando.healthcare.dto.patientrecords;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patientrecords.utils.HealthStatus;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientRecordResponse {

    @Schema(description = "Medical record ID", example = "1")
    private Integer id;
    @Schema(description = "Patient ID", example = "5")
    private Integer patientId;
    @Schema(description = "Patient health status")
    private HealthStatus healthStatus;
    @Schema(description = "Health status notes")
    private String notes;
    @Schema(description = "Patient registration date")
    private LocalDateTime registrationDate;

}
