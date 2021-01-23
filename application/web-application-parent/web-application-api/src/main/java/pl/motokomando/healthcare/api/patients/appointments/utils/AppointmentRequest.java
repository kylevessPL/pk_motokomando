package pl.motokomando.healthcare.api.patients.appointments.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.motokomando.healthcare.api.utils.ValidateDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequest implements Serializable {

    @Schema(description = "Appointment date")
    @NotNull(message = "Appointment date is mandatory")
    @Future(message = "Appointment date must be in the future")
    @ValidateDate(dayFrom = 1, dayTo = 5, hourFrom = 8, hourTo = 18, appointmentDuration = 30)
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime appointmentDate;
    @Schema(description = "Doctor ID", example = "1")
    @NotNull(message = "Doctor ID is mandatory")
    @Min(value = 1, message = "Doctor ID must be a positive integer value")
    private Integer doctorId;

}
