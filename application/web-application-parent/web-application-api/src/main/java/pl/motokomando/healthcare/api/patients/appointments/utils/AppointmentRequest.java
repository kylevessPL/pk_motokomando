package pl.motokomando.healthcare.api.patients.appointments.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequest implements Serializable {

    @ApiModelProperty(value = "Appointment date")
    @NotNull(message = "Appointment date is mandatory")
    @Future(message = "Appointment date must be in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime appointmentDate;
    @ApiModelProperty(value = "Doctor ID", example = "1")
    @NotNull(message = "Doctor ID is mandatory")
    @Min(value = 1, message = "Doctor ID must be a positive integer value")
    private Integer doctorId;

}
