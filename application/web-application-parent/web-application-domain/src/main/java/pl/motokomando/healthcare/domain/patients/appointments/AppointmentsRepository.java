package pl.motokomando.healthcare.domain.patients.appointments;

import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;

import java.time.LocalDateTime;

public interface AppointmentsRepository {

    AppointmentBasic createAppointment(AppointmentRequestCommand data);
    boolean isDateAvailable(LocalDateTime date);

}
