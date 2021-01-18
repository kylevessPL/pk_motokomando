package pl.motokomando.healthcare.api.patients.appointments.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class AppointmentPatchRequest implements Serializable {

    @Schema(description = "Bill ID", example = "1")
    @Min(value = 1, message = "Bill ID must be a positive integer value")
    @Nullable
    private Integer billId;
    @Schema(description = "Doctor ID", example = "1")
    @NotNull(message = "Doctor ID is mandatory")
    @Min(value = 1, message = "Doctor ID must be a positive integer value")
    private Integer doctorId;
    @Schema(description = "Prescription ID", example = "1")
    @Min(value = 1, message = "Prescription ID must be a positive integer value")
    @Nullable
    private Integer prescriptionId;
    @Schema(description = "Diagnosis notes", example = "Cranial tumour in the right frontal lobe")
    @Size(min = 5, max = 200, message = "Diagnosis notes must be between 5 and 200 characters long")
    @Nullable
    private String diagnosis;
    @Schema(description = "Appointment status", allowableValues = "VALID, CANCELLED")
    @NotNull(message = "Appointment status is mandatory")
    private AppointmentStatus appointmentStatus;

}
