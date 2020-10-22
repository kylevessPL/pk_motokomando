package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "patient_records")
@Getter
@Setter
public class PatientRecordsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "patient_records_generator")
    @SequenceGenerator(name="patient_records_generator", sequenceName = "seq_patient_records", allocationSize = 1)
    private Integer id;
    @Column(name = "patient_id", nullable = false)
    private Integer patientId;
    @Column(name = "health_status", nullable = false, length = 15)
    private String healthStatus;
    @Column(name = "notes", length = 100)
    private String notes;
    @Column(name = "registration_date", nullable = false)
    private Timestamp registrationDate;

}
