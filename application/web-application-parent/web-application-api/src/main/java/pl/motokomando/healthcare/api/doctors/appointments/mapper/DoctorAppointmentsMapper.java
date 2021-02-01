package pl.motokomando.healthcare.api.doctors.appointments.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.doctors.appointments.utils.DoctorAppointmentQuery;
import pl.motokomando.healthcare.domain.model.doctors.appointments.DoctorAppointment;
import pl.motokomando.healthcare.domain.model.doctors.appointments.utils.DoctorAppointmentQueryCommand;
import pl.motokomando.healthcare.dto.doctors.appointments.DoctorAppointmentResponse;

import java.util.List;

@Mapper
public interface DoctorAppointmentsMapper {

    DoctorAppointmentQueryCommand mapToCommand(DoctorAppointmentQuery query);
    List<DoctorAppointmentResponse> mapToResponse(List<DoctorAppointment> doctorAppointment);

}
