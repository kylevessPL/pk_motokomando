package pl.motokomando.healthcare.infrastructure.patients.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.patients.appointments.PatientsAppointmentsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PatientsAppointmentsEntityDao;
import pl.motokomando.healthcare.infrastructure.model.PatientsAppointmentsEntity;

@Service
@RequiredArgsConstructor
public class PatientsAppointmentsRepositoryImpl implements PatientsAppointmentsRepository {

    private final PatientsAppointmentsEntityDao dao;

    @Override
    @Transactional
    public void createPatientAppointment(Integer patientId, Integer appointmentId) {
        PatientsAppointmentsEntity patientsAppointmentsEntity = createEntity(patientId, appointmentId);
        dao.save(patientsAppointmentsEntity);
    }

    private PatientsAppointmentsEntity createEntity(Integer patientId, Integer appointmentId) {
        PatientsAppointmentsEntity patientsAppointmentsEntity = new PatientsAppointmentsEntity();
        patientsAppointmentsEntity.setPatientId(patientId);
        patientsAppointmentsEntity.setAppointmentId(appointmentId);
        return patientsAppointmentsEntity;
    }

}
