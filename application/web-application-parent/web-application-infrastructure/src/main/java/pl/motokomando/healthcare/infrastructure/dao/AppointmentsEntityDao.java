package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.motokomando.healthcare.infrastructure.model.AppointmentsEntity;

import java.time.LocalDateTime;

public interface AppointmentsEntityDao extends JpaRepository<AppointmentsEntity, Integer> {

    @Query("SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM AppointmentsEntity e " +
            "WHERE e.appointmentDate = ?1 AND e.appointmentStatus = " +
            "pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentStatus.VALID")
    boolean existsByAppointmentDateAndAndAppointmentStatusEqualsValid(LocalDateTime date);

}
