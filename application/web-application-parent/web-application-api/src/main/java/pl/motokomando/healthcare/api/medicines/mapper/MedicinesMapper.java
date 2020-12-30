package pl.motokomando.healthcare.api.medicines.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.medicines.utils.MedicineQuery;
import pl.motokomando.healthcare.domain.model.medicines.Medicine;
import pl.motokomando.healthcare.domain.model.medicines.utils.MedicineCommand;
import pl.motokomando.healthcare.dto.medicines.MedicineResponse;

import java.util.List;

@Mapper
public interface MedicinesMapper {

    MedicineCommand mapToCommand(MedicineQuery query);
    MedicineResponse mapToResponse(Medicine medicine);
    List<MedicineResponse> mapToResponse(List<Medicine> medicines);

}
