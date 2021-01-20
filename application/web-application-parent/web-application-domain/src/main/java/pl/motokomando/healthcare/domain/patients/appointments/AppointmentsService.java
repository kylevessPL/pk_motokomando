package pl.motokomando.healthcare.domain.patients.appointments;

import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasicPage;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestParamsCommand;
import pl.motokomando.healthcare.domain.model.utils.Basic;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;

public interface AppointmentsService {

    AppointmentBasicPage getAllAppointments(Integer patientId, BasicPagedQueryCommand command);
    Appointment getAppointment(AppointmentRequestParamsCommand command);
    Basic createAppointment(Integer patientId, AppointmentRequestCommand command);
    void updateAppointment(AppointmentPatchRequestCommand command);

}
