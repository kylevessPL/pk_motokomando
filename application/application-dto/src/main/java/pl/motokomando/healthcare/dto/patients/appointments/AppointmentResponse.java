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
public class AppointmentResponse {

    @Schema(description = "Appointment ID", example = "1")
    private Integer id;
    @Schema(description = "Schedule date")
    private LocalDateTime scheduleDate;
    @Schema(description = "Appointment date")
    private LocalDateTime appointmentDate;
    @Schema(description = "Bill ID", example = "1")
    private Integer billId;
    @Schema(description = "Doctor ID", example = "1")
    private Integer doctorId;
    @Schema(description = "Prescription ID", example = "1")
    private Integer prescriptionId;
    @Schema(description = "Diagnosis notes", example = "Cranial tumour in the right frontal lobe")
    private String diagnosis;
    @Schema(description = "Appointment status")
    private AppointmentStatus appointmentStatus;

}
