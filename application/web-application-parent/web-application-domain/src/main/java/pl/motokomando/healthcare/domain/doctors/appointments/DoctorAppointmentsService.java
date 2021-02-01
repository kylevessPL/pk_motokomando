package pl.motokomando.healthcare.domain.doctors.appointments;

import pl.motokomando.healthcare.domain.model.doctors.appointments.DoctorAppointment;
import pl.motokomando.healthcare.domain.model.doctors.appointments.utils.DoctorAppointmentQueryCommand;

import java.util.List;

public interface DoctorAppointmentsService {

    List<DoctorAppointment> getDoctorAppointments(Integer id, DoctorAppointmentQueryCommand command);

}
