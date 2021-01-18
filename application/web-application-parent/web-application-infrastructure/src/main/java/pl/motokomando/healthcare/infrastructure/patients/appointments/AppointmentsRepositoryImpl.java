package pl.motokomando.healthcare.infrastructure.patients.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.appointments.Appointment;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasicPage;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.PageProperties;
import pl.motokomando.healthcare.domain.model.utils.SortProperties;
import pl.motokomando.healthcare.domain.patients.appointments.AppointmentsRepository;
import pl.motokomando.healthcare.infrastructure.dao.AppointmentsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.AppointmentsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.AppointmentsEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentsRepositoryImpl implements AppointmentsRepository {

    private final AppointmentsEntityDao dao;
    private final AppointmentsEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public AppointmentBasicPage getAllAppointmentsByIdIn(List<Integer> appointmentIdList, PageProperties pageProperties, SortProperties sortProperties) {
        Integer page = pageProperties.getPage();
        Integer size = pageProperties.getSize();
        Sort sort = createSortProperty(sortProperties);
        Page<AppointmentsEntity> result = getAllPaged(appointmentIdList, page, size, sort);
        return mapper.mapToAppointmentBasicPage(
                result.hasContent() ? result.getContent() : Collections.emptyList(),
                result.isFirst(),
                result.isLast(),
                result.hasPrevious(),
                result.hasNext(),
                result.getNumber() + 1,
                result.getTotalPages(),
                result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment getAppointmentById(Integer id) {
        return mapper.mapToAppointment(dao.getOne(id));
    }

    @Override
    @Transactional
    public void updateAppointment(AppointmentPatchRequestCommand data) {
        AppointmentsEntity appointmentsEntity = createEntity(data);
        dao.save(appointmentsEntity);
    }

    @Override
    @Transactional
    public AppointmentBasic createAppointment(AppointmentRequestCommand data) {
        AppointmentsEntity appointmentsEntity = createEntity(data);
        Integer id = dao.save(appointmentsEntity).getId();
        return mapper.mapToAppointmentBasic(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isDateAvailable(LocalDateTime date) {
        return !dao.existsByAppointmentDateAndAndAppointmentStatusEqualsValid(date);
    }

    private Sort createSortProperty(SortProperties sortProperties) {
        String sortBy = sortProperties.getSortBy();
        Sort.Direction sortDir = Sort.Direction.valueOf(sortProperties.getSortDir().name());
        return Sort.by(sortDir, sortBy);
    }

    private Page<AppointmentsEntity> getAllPaged(
            List<Integer> appointmentIdList,
            Integer page,
            Integer size,
            Sort sort) {
        Page<AppointmentsEntity> result = dao.findAllByIdIn(appointmentIdList, PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = dao.findAllByIdIn(appointmentIdList, PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }

    private AppointmentsEntity createEntity(AppointmentRequestCommand data) {
        AppointmentsEntity appointmentsEntity = new AppointmentsEntity();
        appointmentsEntity.setAppointmentDate(data.getAppointmentDate());
        appointmentsEntity.setDoctorId(data.getDoctorId());
        return appointmentsEntity;
    }

    private AppointmentsEntity createEntity(AppointmentPatchRequestCommand data) {
        AppointmentsEntity appointmentsEntity = new AppointmentsEntity();
        appointmentsEntity.setId(data.getId());
        appointmentsEntity.setScheduleDate(Timestamp.valueOf(data.getScheduleDate()));
        appointmentsEntity.setAppointmentDate(data.getAppointmentDate());
        appointmentsEntity.setBillId(data.getBillId());
        appointmentsEntity.setDoctorId(data.getDoctorId());
        appointmentsEntity.setPrescriptionId(data.getPrescriptionId());
        appointmentsEntity.setGiagnosis(data.getDiagnosis());
        appointmentsEntity.setAppointmentStatus(data.getAppointmentStatus());
        return appointmentsEntity;
    }

}