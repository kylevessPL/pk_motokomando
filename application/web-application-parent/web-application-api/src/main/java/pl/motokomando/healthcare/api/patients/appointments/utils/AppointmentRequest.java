package pl.motokomando.healthcare.api.patients.appointments.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequest implements Serializable {

    @Schema(description = "Appointment date")
    @NotNull(message = "Appointment date is mandatory")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;
    @Schema(description = "Doctor ID", example = "1")
    @NotNull(message = "Doctor ID is mandatory")
    @Min(value = 1, message = "Doctor ID must be a positive integer value")
    private Integer doctorId;

}
