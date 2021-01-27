package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.doctors.utils.MedicalSpecialty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "specialties")
@Getter
@Setter
public class SpecialtiesEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "specialties_generator")
    @SequenceGenerator(name = "specialties_generator", sequenceName = "seq_specialties", allocationSize = 1)
    private Integer id;
    @Column(name = "specialty", nullable = false, length = 30)
    @Enumerated(STRING)
    private MedicalSpecialty specialty;

}
