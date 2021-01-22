package pl.motokomando.healthcare.dto.patients.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime appointmentDate;
    @Schema(description = "Appointment status")
    private AppointmentStatus appointmentStatus;

}