package pl.motokomando.healthcare.dto.prescriptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionResponse {

    @Schema(description = "Prescription ID", example = "1")
    private Integer id;
    @Schema(description = "Prescription issue date")
    private LocalDateTime issueDate;
    @Schema(description = "Prescription expiration date")
    private LocalDate expirationDate;
    @Schema(description = "Prescription additional notes", example = "Take 5ml orally at 8 a.m., 12 noon and 8 p.m. daily for 7 days")
    private String notes;

}
