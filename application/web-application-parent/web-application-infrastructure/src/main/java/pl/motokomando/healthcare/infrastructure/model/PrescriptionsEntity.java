package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
public class PrescriptionsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "prescriptions_generator")
    @SequenceGenerator(name="prescriptions_generator", sequenceName = "seq_prescriptions", allocationSize = 1)
    private Integer id;
    @Column(name = "issue_date", nullable = false)
    @CreationTimestamp
    private Timestamp issueDate;
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    @Column(name = "notes", length = 100)
    private String notes;

}
