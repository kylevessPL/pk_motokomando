package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PatientsAppointmentsEntity;

public interface PatientsAppointmentsEntityDao extends JpaRepository<PatientsAppointmentsEntity, Integer> {

    boolean existsByPatientIdAndAppointmentId(Integer patientId, Integer appointmentId);

}
