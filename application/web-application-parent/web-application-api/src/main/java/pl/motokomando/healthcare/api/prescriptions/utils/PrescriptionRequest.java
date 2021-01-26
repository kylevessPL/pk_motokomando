package pl.motokomando.healthcare.api.prescriptions.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionRequest {

    @Schema(description = "Prescription expiration date")
    @NotNull(message = "Expiration date is mandatory")
    @Future(message = "Expiration date must be in the future")
    @DateTimeFormat(iso = DATE)
    private LocalDate expirationDate;
    @Schema(description = "Prescription additional notes", example = "Take 5ml orally at 8 a.m., 12 noon and 8 p.m. daily for 7 days")
    @Size(min = 5, max = 100, message = "Notes must be between 5 and 100 characters long")
    @Nullable
    private String notes;

}
