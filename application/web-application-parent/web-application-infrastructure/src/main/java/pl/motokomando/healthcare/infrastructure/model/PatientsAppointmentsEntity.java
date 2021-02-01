package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "patients_appointments")
@Getter
@Setter
public class PatientsAppointmentsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "patients_appointments_generator")
    @SequenceGenerator(name = "patients_appointments_generator", sequenceName = "seq_patients_appointments", allocationSize = 1)
    private Integer id;
    @Column(name = "patient_id", nullable = false)
    private Integer patientId;
    @Column(name = "appointment_id", nullable = false)
    private Integer appointmentId;

}
