package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.AppointmentsEntity;

import java.time.LocalDateTime;

public interface AppointmentsEntityDao extends JpaRepository<AppointmentsEntity, Integer> {

    boolean existsByAppointmentDate(LocalDateTime date);

}
