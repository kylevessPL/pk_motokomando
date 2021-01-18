package pl.motokomando.healthcare.dto.patients.appointments;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.patients.appointments.utils.AppointmentStatus;

import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class AppointmentResponse {

    @ApiModelProperty(value = "Appointment ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "Schedule date")
    private LocalDateTime scheduleDate;
    @ApiModelProperty(value = "Appointment date")
    private LocalDateTime appointmentDate;
    @ApiModelProperty(value = "Bill ID", example = "1")
    private Integer billId;
    @ApiModelProperty(value = "Doctor ID", example = "1")
    private Integer doctorId;
    @ApiModelProperty(value = "Prescription ID", example = "1")
    private Integer prescriptionId;
    @ApiModelProperty(value = "Diagnosis notes", example = "Cranial tumour in the right frontal lobe")
    private String diagnosis;
    @ApiModelProperty(value = "Appointment status")
    private AppointmentStatus appointmentStatus;

}
