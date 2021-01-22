package pl.motokomando.healthcare.domain.model.patients.appointments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.bills.Bill;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentStatus;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionFull;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class AppointmentFull {

    private final Integer id;
    private final LocalDateTime scheduleDate;
    private final LocalDateTime appointmentDate;
    private final Bill bill;
    private final Doctor doctor;
    private final PrescriptionFull prescription;
    private final String diagnosis;
    private final AppointmentStatus appointmentStatus;

}
