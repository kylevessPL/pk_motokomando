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
import java.math.BigDecimal;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "bills")
@Getter
@Setter
public class BillsEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "bills_generator")
    @SequenceGenerator(name="bills_generator", sequenceName = "seq_bills", allocationSize = 1)
    private Integer id;
    @Column(name = "issue_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp issueDate;
    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;

}
