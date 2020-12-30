package pl.motokomando.healthcare.domain.medicines;

import pl.motokomando.healthcare.domain.model.medicines.Medicine;
import pl.motokomando.healthcare.domain.model.medicines.utils.MedicineCommand;

import java.util.List;

public interface MedicinesService {

    Medicine getMedicineByProductNDC(String productNDC);
    List<Medicine> searchMedicine(MedicineCommand query);

}
