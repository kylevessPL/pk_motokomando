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
@Table(name = "doctors_specialties")
@Getter
@Setter
public class DoctorsSpecialtiesEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "doctors_specialties_generator")
    @SequenceGenerator(name = "doctors_specialties_generator", sequenceName = "seq_doctors_specialties", allocationSize = 1)
    private Integer id;
    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;
    @Column(name = "specialty_id", nullable = false)
    private Integer specialtyId;

}
