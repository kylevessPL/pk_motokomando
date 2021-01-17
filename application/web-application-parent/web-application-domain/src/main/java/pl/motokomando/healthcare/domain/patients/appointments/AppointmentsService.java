package pl.motokomando.healthcare.domain.patients.appointments;

import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;

public interface AppointmentsService {

    AppointmentBasic createAppointment(Integer patientId, AppointmentRequestCommand request);

}
