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
@Table(name = "prescription_medicines")
@Getter
@Setter
public class PrescriptionMedicinesEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "prescription_medicines_generator")
    @SequenceGenerator(name = "prescription_medicines_generator", sequenceName = "seq_prescription_medicines", allocationSize = 1)
    private Integer id;
    @Column(name = "prescription_id", nullable = false)
    private Integer prescriptionId;
    @Column(name = "medicine_ndc", nullable = false, length = 10)
    private String productNDC;

}
