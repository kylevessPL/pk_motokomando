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
@Table(name = "medicines")
@Getter
@Setter
public class MedicinesEntity {

    @Id @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "medicines_generator")
    @SequenceGenerator(name="medicines_generator", sequenceName = "seq_medicines", allocationSize = 1)
    private Integer id;
    @Column(name = "company", nullable = false, length = 50)
    private String company;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "notes", length = 100)
    private String notes;

}
