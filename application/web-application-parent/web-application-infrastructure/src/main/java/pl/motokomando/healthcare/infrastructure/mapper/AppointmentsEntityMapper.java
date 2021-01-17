package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.infrastructure.model.AppointmentsEntity;

@Component
public class AppointmentsEntityMapper {

    public Appointment mapToAppointment(AppointmentsEntity appointmentsEntity) {
        return createAppointment(appointmentsEntity);
    }

    public AppointmentBasic mapToAppointmentBasic(Integer id) {
        return createAppointmentBasic(id);
    }

    private AppointmentBasic createAppointmentBasic(Integer id) {
        return new AppointmentBasic(id);
    }

    private Appointment createAppointment(AppointmentsEntity appointmentsEntity) {
        return new Appointment(
                appointmentsEntity.getId(),
                appointmentsEntity.getScheduleDate().toLocalDateTime(),
                appointmentsEntity.getAppointmentDate(),
                appointmentsEntity.getBillId(),
                appointmentsEntity.getDoctorId(),
                appointmentsEntity.getPrescriptionId(),
                appointmentsEntity.getGiagnosis(),
                appointmentsEntity.getAppointmentStatus()
        );
    }

}
