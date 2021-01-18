package pl.motokomando.healthcare.infrastructure.patients.appointments;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class AppointmentsRepositoryImpl implements AppointmentsRepository {

    private final AppointmentsEntityDao dao;
    private final AppointmentsEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public AppointmentBasicPage getAllAppointments(PageProperties pageProperties, SortProperties sortProperties) {
        return null;
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