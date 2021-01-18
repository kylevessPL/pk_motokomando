package pl.motokomando.healthcare.dto.patients.appointments.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class AppointmentBasicPaged {

    @ApiModelProperty(value = "Appointment ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "Appointment date")
    private LocalDateTime appointmentDate;
    @ApiModelProperty(value = "Appointment status")
    private AppointmentStatus appointmentStatus;

}
