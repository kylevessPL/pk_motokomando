package pl.motokomando.healthcare.infrastructure.patients.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.model.patients.appointments.AppointmentBasic;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentRequestCommand;
import pl.motokomando.healthcare.domain.patients.appointments.AppointmentsRepository;
import pl.motokomando.healthcare.infrastructure.dao.AppointmentsEntityDao;
import pl.motokomando.healthcare.infrastructure.mapper.AppointmentsEntityMapper;
import pl.motokomando.healthcare.infrastructure.model.AppointmentsEntity;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentsRepositoryImpl implements AppointmentsRepository {

    private final AppointmentsEntityDao dao;
    private final AppointmentsEntityMapper mapper;

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
        return !dao.existsByAppointmentDateAndAndAppointmentStatusEqualsScheduled(date);
    }

    private AppointmentsEntity createEntity(AppointmentRequestCommand data) {
        AppointmentsEntity appointmentsEntity = new AppointmentsEntity();
        appointmentsEntity.setAppointmentDate(data.getAppointmentDate());
        appointmentsEntity.setDoctorId(data.getDoctorId());
        return appointmentsEntity;
    }

}
