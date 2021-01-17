package pl.motokomando.healthcare.domain.patients.appointments;

import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestParamsCommand;

public interface AppointmentsService {

    Appointment getAppointment(AppointmentRequestParamsCommand command);
    AppointmentBasic createAppointment(Integer patientId, AppointmentRequestCommand command);
    void updateAppointment(AppointmentPatchRequestCommand command);

}
