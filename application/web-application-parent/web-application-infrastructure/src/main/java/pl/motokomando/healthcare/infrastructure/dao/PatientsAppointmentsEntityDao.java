package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PatientsAppointmentsEntity;

import java.util.List;

public interface PatientsAppointmentsEntityDao extends JpaRepository<PatientsAppointmentsEntity, Integer> {

    List<PatientsAppointmentsEntity> findAllByPatientId(Integer patientId);
    boolean existsByPatientIdAndAppointmentId(Integer patientId, Integer appointmentId);

}
