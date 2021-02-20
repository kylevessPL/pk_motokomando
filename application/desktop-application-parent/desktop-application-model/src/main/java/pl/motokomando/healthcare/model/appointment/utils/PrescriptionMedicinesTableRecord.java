package pl.motokomando.healthcare.model.appointment.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class PrescriptionMedicinesTableRecord {

    private final Integer medicineId;
    private final MedicinesTableRecord record;

}
