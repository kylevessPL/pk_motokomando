package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class AppointmentsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "appointments_generator")
    @SequenceGenerator(name="appointments_generator", sequenceName = "seq_appointments", allocationSize = 1)
    private Integer id;
    @Column(name = "schedule_date", nullable = false)
    private Timestamp scheduleDate;
    @Column(name = "appointment_date", nullable = false)
    private Date appointmentDate;
    @Column(name = "bill_id", nullable = false)
    private Integer billId;
    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;
    @Column(name = "prescription_id")
    private Integer prescriptionId;
    @Column(name = "giagnosis", length = 200)
    private String giagnosis;
    @Column(name = "appointment_status", nullable = false, length = 10)
    private String appointmentStatus;

}
