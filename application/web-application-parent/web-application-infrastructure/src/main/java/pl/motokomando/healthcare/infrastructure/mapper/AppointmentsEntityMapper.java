package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;

@Component
public class AppointmentsEntityMapper {

    public AppointmentBasic mapToAppointmentBasic(Integer id) {
        return createAppointmentBasic(id);
    }

    private AppointmentBasic createAppointmentBasic(Integer id) {
        return new AppointmentBasic(id);
    }

}
