package pl.motokomando.healthcare.api.patients.appointments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.motokomando.healthcare.api.mapper.PageMetaMapper;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentPagedQuery;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentPatchRequest;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequest;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequestParams;
import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasicPage;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestParamsCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicPageResponse;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicResponse;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentResponse;

@Mapper(uses = { AppointmentBasicPagedMapper.class, PageMetaMapper.class })
public interface AppointmentsMapper {

    BasicPagedQueryCommand mapToCommand(AppointmentPagedQuery query);
    AppointmentResponse mapToResponse(Appointment appointment);
    AppointmentBasicResponse mapToBasicResponse(AppointmentBasic appointmentBasic);
    AppointmentPatchRequest mapToRequest(AppointmentResponse response);
    AppointmentRequestParamsCommand mapToCommand(AppointmentRequestParams request);
    AppointmentRequestCommand mapToCommand(AppointmentRequest request);
    AppointmentPatchRequestCommand mapToCommand(AppointmentResponse response);
    AppointmentBasicPageResponse mapToResponse(AppointmentBasicPage appointmentBasicPage);
    void update(AppointmentPatchRequest request, @MappingTarget AppointmentPatchRequestCommand command);

}
