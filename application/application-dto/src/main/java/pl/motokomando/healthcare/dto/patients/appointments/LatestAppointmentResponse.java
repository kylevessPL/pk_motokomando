package pl.motokomando.healthcare.dto.patients.appointments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class LatestAppointmentResponse {

    private LocalDateTime appointmentDate;
    private String doctorFullName;
    private String diagnosis;

}
