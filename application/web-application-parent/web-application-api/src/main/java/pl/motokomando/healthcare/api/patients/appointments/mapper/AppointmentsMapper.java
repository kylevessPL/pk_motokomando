package pl.motokomando.healthcare.api.patients.appointments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.motokomando.healthcare.api.bills.mapper.BillsMapper;
import pl.motokomando.healthcare.api.doctors.mapper.DoctorsMapper;
import pl.motokomando.healthcare.api.mapper.PageMetaMapper;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentPagedQuery;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentPatchRequest;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequest;
import pl.motokomando.healthcare.api.patients.appointments.utils.AppointmentRequestParams;
import pl.motokomando.healthcare.api.prescriptions.mapper.PrescriptionMapper;
import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasicPage;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentFull;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestParamsCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentBasicPageResponse;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentFullResponse;
import pl.motokomando.healthcare.dto.patients.appointments.AppointmentResponse;

@Mapper(uses = { AppointmentBasicPagedMapper.class, PageMetaMapper.class, BillsMapper.class, DoctorsMapper.class, PrescriptionMapper.class })
public interface AppointmentsMapper {

    BasicPagedQueryCommand mapToCommand(AppointmentPagedQuery query);
    AppointmentResponse mapToResponse(Appointment appointment);
    AppointmentFullResponse mapToResponse(AppointmentFull appointmentFull);
    AppointmentPatchRequest mapToRequest(AppointmentResponse response);
    AppointmentRequestParamsCommand mapToCommand(AppointmentRequestParams request);
    AppointmentRequestCommand mapToCommand(AppointmentRequest request);
    AppointmentPatchRequestCommand mapToCommand(AppointmentResponse response);
    AppointmentBasicPageResponse mapToResponse(AppointmentBasicPage appointmentBasicPage);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scheduleDate", ignore = true)
    @Mapping(target = "appointmentDate", ignore = true)
    void update(AppointmentPatchRequest request, @MappingTarget AppointmentPatchRequestCommand command);

}
