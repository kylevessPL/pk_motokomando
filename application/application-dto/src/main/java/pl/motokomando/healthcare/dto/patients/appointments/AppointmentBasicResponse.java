package pl.motokomando.healthcare.dto.patients.appointments;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class AppointmentBasicResponse {

    @ApiModelProperty(value = "Appointment ID", example = "1")
    private Integer id;

}
