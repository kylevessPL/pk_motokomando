package pl.motokomando.healthcare.api.patients.appointments.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequest;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicResponse;

@Mapper
public interface AppointmentsMapper {

    AppointmentBasicResponse mapToBasicResponse(AppointmentBasic appointmentBasic);
    AppointmentRequestCommand mapToCommand(AppointmentRequest request);

}
