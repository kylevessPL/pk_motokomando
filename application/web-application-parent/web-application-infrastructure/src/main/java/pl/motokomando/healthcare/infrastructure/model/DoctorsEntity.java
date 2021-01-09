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
@Table(name = "doctors")
@Getter
@Setter
public class DoctorsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "doctors_generator")
    @SequenceGenerator(name="doctors_generator", sequenceName = "seq_doctors", allocationSize = 1)
    private Integer id;
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
    @Column(name = "specialty", nullable = false, length = 30)
    @Enumerated(STRING)
    private MedicalSpecialty specialty;

}
