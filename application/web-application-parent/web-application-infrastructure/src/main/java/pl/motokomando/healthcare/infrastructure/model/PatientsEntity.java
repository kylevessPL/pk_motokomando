package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.patients.utils.BloodType;
import pl.motokomando.healthcare.domain.model.patients.utils.DocumentType;
import pl.motokomando.healthcare.domain.model.patients.utils.Sex;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class PatientsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "patients_generator")
    @SequenceGenerator(name="patients_generator", sequenceName = "seq_patients", allocationSize = 1)
    private Integer id;
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "sex", nullable = false, length = 6)
    @Enumerated(STRING)
    private Sex sex;
    @Column(name = "blood_type", nullable = false, length = 5)
    @Enumerated(STRING)
    private BloodType bloodType;
    @Column(name = "street_name", nullable = false, length = 30)
    private String streetName;
    @Column(name = "house_number", nullable = false, length = 10)
    private String houseNumber;
    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;
    @Column(name = "city", nullable = false, length = 30)
    private String city;
    @Column(name = "document_type", nullable = false, length = 10)
    @Enumerated(STRING)
    private DocumentType documentType;
    @Column(name = "document_id", nullable = false, length = 10)
    private String documentId;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

}
