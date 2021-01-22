package pl.motokomando.healthcare.dto.patients.appointments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.appointments.utils.AppointmentStatus;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class AppointmentBasicPagedResponse {

    @Schema(description = "Appointment ID", example = "1")
    private Integer id;
    @Schema(description = "Appointment date")
    private LocalDateTime appointmentDate;
    @Schema(description = "Appointment status")
    private AppointmentStatus appointmentStatus;

}
