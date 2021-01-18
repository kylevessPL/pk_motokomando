package pl.motokomando.healthcare.api.prescriptions.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
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

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionRequest {

    @Schema(description = "Prescription expiration date")
    @NotNull(message = "Expiration date is mandatory")
    @Future(message = "Expiration date must be in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;
    @Schema(description = "Prescription additional notes")
    @Size(min = 5, max = 100, message = "Notes must be between 5 and 100 characters long")
    @Nullable
    private String notes;

}
