package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class DoctorsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "doctors_generator")
    @SequenceGenerator(name = "doctors_generator", sequenceName = "seq_doctors", allocationSize = 1)
    private Integer id;
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @OneToMany
    @JoinTable(
            name = "doctors_specialties",
            joinColumns = { @JoinColumn(name = "doctor_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "specialty_id", referencedColumnName = "id", unique = true) })
    private Set<SpecialtiesEntity> specialties;

}
