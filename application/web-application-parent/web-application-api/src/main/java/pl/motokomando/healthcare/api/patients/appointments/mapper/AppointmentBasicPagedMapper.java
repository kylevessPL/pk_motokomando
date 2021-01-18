package pl.motokomando.healthcare.api.patients.appointments.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.dto.patients.appointments.utils.AppointmentBasicPaged;

@Mapper
public interface AppointmentBasicPagedMapper {

    AppointmentBasicPaged map(pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentBasicPaged appointmentBasicPaged);

}
