package pl.motokomando.healthcare.api.patients.appointments.utils;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequestParams implements Serializable {

    @Parameter(description = "Patient ID", example = "1")
    @NotNull(message = "Patient ID is mandatory")
    @Min(value = 1, message = "Patient ID must be a positive integer value")
    private Integer patientId;
    @Parameter(description = "Appointment ID", example = "1")
    @NotNull(message = "Appointment ID is mandatory")
    @Min(value = 1, message = "Appointment ID must be a positive integer value")
    private Integer appointmentId;

}
