package pl.motokomando.healthcare.dto.doctors.appointments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.utils.AppointmentStatus;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class DoctorAppointmentResponse {

    @Schema(description = "Appointment date")
    private LocalDateTime appointmentDate;
    @Schema(description = "Appointment status")
    private AppointmentStatus appointmentStatus;

}
