package pl.motokomando.healthcare.domain.model.patients.appointments.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.utils.AppointmentStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentPatchRequestCommand {

    private Integer id;
    private LocalDateTime scheduleDate;
    private LocalDateTime appointmentDate;
    private Integer billId;
    private Integer doctorId;
    private Integer prescriptionId;
    private String diagnosis;
    private AppointmentStatus appointmentStatus;

}
