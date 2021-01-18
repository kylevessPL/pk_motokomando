package pl.motokomando.healthcare.infrastructure.patients.appointments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.motokomando.healthcare.domain.patients.appointments.PatientsAppointmentsRepository;
import pl.motokomando.healthcare.infrastructure.dao.PatientsAppointmentsEntityDao;
import pl.motokomando.healthcare.infrastructure.model.PatientsAppointmentsEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientsAppointmentsRepositoryImpl implements PatientsAppointmentsRepository {

    private final PatientsAppointmentsEntityDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getPatientAppointmentIdList(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public void createPatientAppointment(Integer patientId, Integer appointmentId) {
        PatientsAppointmentsEntity patientsAppointmentsEntity = createEntity(patientId, appointmentId);
        dao.save(patientsAppointmentsEntity);
    }

    @Override
    public boolean patientAppointmentExistsByPatientIdAndAppointmentId(Integer patientId, Integer appointmentId) {
        return dao.existsByPatientIdAndAppointmentId(patientId, appointmentId);
    }

    private PatientsAppointmentsEntity createEntity(Integer patientId, Integer appointmentId) {
        PatientsAppointmentsEntity patientsAppointmentsEntity = new PatientsAppointmentsEntity();
        patientsAppointmentsEntity.setPatientId(patientId);
        patientsAppointmentsEntity.setAppointmentId(appointmentId);
        return patientsAppointmentsEntity;
    }

}
