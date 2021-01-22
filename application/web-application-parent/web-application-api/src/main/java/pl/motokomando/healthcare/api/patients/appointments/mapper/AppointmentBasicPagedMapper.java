package pl.motokomando.healthcare.api.patients.appointments.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentBasicPaged;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicPagedResponse;

@Mapper
public interface AppointmentBasicPagedMapper {

    AppointmentBasicPagedResponse map(AppointmentBasicPaged appointmentBasicPaged);

}
