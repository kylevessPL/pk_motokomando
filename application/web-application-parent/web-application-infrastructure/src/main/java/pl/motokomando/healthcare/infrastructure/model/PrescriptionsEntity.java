package pl.motokomando.healthcare.infrastructure.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
    private Timestamp issueDate;
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;
    @Column(name = "notes", length = 100)
    private String notes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "prescriptions_medicines",
            joinColumns = { @JoinColumn(name="prescription_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name="medicine_id", referencedColumnName="id", unique=true) })
    private List<MedicinesEntity> medicines;

}
