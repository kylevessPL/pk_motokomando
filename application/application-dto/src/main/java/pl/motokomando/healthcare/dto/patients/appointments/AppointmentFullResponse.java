package pl.motokomando.healthcare.dto.patients.appointments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.bills.BillResponse;
import pl.motokomando.healthcare.dto.doctors.DoctorResponse;
import pl.motokomando.healthcare.dto.patients.appointments.utils.AppointmentStatus;
import pl.motokomando.healthcare.dto.prescriptions.PrescriptionFullResponse;

import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class AppointmentFullResponse {

    @Schema(description = "Appointment ID", example = "1")
    private Integer id;
    @Schema(description = "Schedule date")
    private LocalDateTime scheduleDate;
    @Schema(description = "Appointment date")
    private LocalDateTime appointmentDate;
    @Schema(description = "Bill details")
    private BillResponse bill;
    @Schema(description = "Doctor details")
    private DoctorResponse doctor;
    @Schema(description = "Prescription details")
    private PrescriptionFullResponse prescription;
    @Schema(description = "Diagnosis notes", example = "Cranial tumour in the right frontal lobe")
    private String diagnosis;
    @Schema(description = "Appointment status")
    private AppointmentStatus appointmentStatus;

}
