package pl.motokomando.healthcare.dto.patients;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.appointments.LatestAppointmentResponse;
import pl.motokomando.healthcare.dto.patients.records.CurrentHealthResponse;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientHealthInfoResponse {

    @Schema(description = "Patient ID", example = "1")
    private Integer patientId;
    @Schema(description = "Current health status information")
    private CurrentHealthResponse currentHealth;
    @Schema(description = "Latest appointment date and diagnosis")
    private LatestAppointmentResponse latestAppointment;

}
