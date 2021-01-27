package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;
import static pl.motokomando.healthcare.domain.model.patients.appointments.utils.AppointmentStatus.VALID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class AppointmentsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "appointments_generator")
    @SequenceGenerator(name = "appointments_generator", sequenceName = "seq_appointments", allocationSize = 1)
    private Integer id;
    @Column(name = "schedule_date", nullable = false)
    @CreationTimestamp
    private Timestamp scheduleDate;
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;
    @Column(name = "bill_id")
    private Integer billId;
    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;
    @Column(name = "prescription_id")
    private Integer prescriptionId;
    @Column(name = "giagnosis", length = 200)
    private String giagnosis;
    @Column(name = "appointment_status", nullable = false, length = 10)
    @Enumerated(STRING)
    private AppointmentStatus appointmentStatus = VALID;

}
