package pl.motokomando.healthcare.dto.patients.appointments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class AppointmentBasicResponse {

    @Schema(description = "Appointment ID", example = "1")
    private Integer id;

}
