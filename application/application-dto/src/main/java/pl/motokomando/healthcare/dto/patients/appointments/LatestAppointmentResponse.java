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

    @Schema(description = "Appointment date")
    private LocalDateTime appointmentDate;
    @Schema(description = "Appointment doctor full name", example = "John Doe")
    private String doctorFullName;
    @Schema(description = "Generic product name", example = "Cranial tumour in the right frontal lobe")
    private String diagnosis;

}
