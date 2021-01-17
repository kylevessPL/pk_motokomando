package pl.motokomando.healthcare.api.patients.appointments.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequestParams implements Serializable {

    @ApiModelProperty(value = "Patient ID", example = "1")
    @NotNull(message = "Patient ID is mandatory")
    @Min(value = 1, message = "Patient ID must be a positive integer value")
    private Integer patientId;
    @ApiModelProperty(value = "Appointment ID", example = "1")
    @NotNull(message = "Appointment ID is mandatory")
    @Min(value = 1, message = "Appointment ID must be a positive integer value")
    private Integer appointmentId;

}
