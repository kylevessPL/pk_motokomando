package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.motokomando.healthcare.infrastructure.model.AppointmentsEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentsEntityDao extends JpaRepository<AppointmentsEntity, Integer> {

    Page<AppointmentsEntity> findAllByIdIn(List<Integer> appointmentIdList, Pageable pageable);
    AppointmentsEntity findFirstByIdInOrderByAppointmentDateDesc(List<Integer> appointmentIdList);
    @Query("SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM AppointmentsEntity e " +
            "WHERE e.appointmentDate = ?1 AND e.appointmentStatus = " +
            "pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentStatus.VALID")
    boolean existsByAppointmentDateAndAndAppointmentStatusEqualsValid(LocalDateTime date);

}
