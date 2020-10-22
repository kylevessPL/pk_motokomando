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
@Table(name = "prescriptions_medicines")
@Getter
@Setter
public class PrescriptionsMedicinesEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "prescriptions_medicines_generator")
    @SequenceGenerator(name="prescriptions_medicines_generator", sequenceName = "seq_prescriptions_medicines", allocationSize = 1)
    private Integer id;
    @Column(name = "prescription_id", nullable = false)
    private Integer prescriptionId;
    @Column(name = "medicine_id", nullable = false)
    private Integer medicineId;

}
