package pl.motokomando.healthcare.dto.prescriptions;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime issueDate;
    @Schema(description = "Prescription expiration date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
    @Schema(description = "Prescription additional notes")
    private String notes;

}
