package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasicPage;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentBasicPaged;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;
import pl.motokomando.healthcare.infrastructure.model.AppointmentsEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentsEntityMapper {

    public Appointment mapToAppointment(AppointmentsEntity appointmentsEntity) {
        return createAppointment(appointmentsEntity);
    }

    public AppointmentBasicPage mapToAppointmentBasicPage(List<AppointmentsEntity> appointmentsEntityList, boolean isFirst, boolean isLast, boolean hasPrev, boolean hasNext, Integer currentPage, Integer totalPage, Long totalCount) {
        List<AppointmentBasicPaged> appointmentBasicPagedList = appointmentsEntityList
                .stream()
                .map(this::createAppointmentBasicPaged)
                .collect(Collectors.toList());
        return new AppointmentBasicPage(
                new PageMeta(isFirst, isLast, hasPrev, hasNext, currentPage, totalPage, totalCount),
                appointmentBasicPagedList);
    }

    public AppointmentBasic mapToAppointmentBasic(Integer id) {
        return createAppointmentBasic(id);
    }

    private AppointmentBasicPaged createAppointmentBasicPaged(AppointmentsEntity appointmentsEntity) {
        return new AppointmentBasicPaged(
                appointmentsEntity.getId(),
                appointmentsEntity.getAppointmentDate(),
                appointmentsEntity.getAppointmentStatus());
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
