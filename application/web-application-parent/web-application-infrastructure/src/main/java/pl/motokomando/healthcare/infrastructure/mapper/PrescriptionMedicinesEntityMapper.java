package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.PrescriptionMedicine;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionMedicinesEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PrescriptionMedicinesEntityMapper {

    public List<PrescriptionMedicine> mapToPrescriptionMedicineList(List<PrescriptionMedicinesEntity> prescriptionMedicinesEntityList) {
        return prescriptionMedicinesEntityList
                .stream()
                .map(this::createPrescriptionMedicine)
                .collect(Collectors.toList());
    }

    public Optional<PrescriptionMedicine> mapToPrescriptionMedicine(Optional<PrescriptionMedicinesEntity> prescriptionMedicinesEntity) {
        return prescriptionMedicinesEntity.map(this::createPrescriptionMedicine);
    }

    private PrescriptionMedicine createPrescriptionMedicine(PrescriptionMedicinesEntity prescriptionMedicinesEntity) {
        return new PrescriptionMedicine(
                prescriptionMedicinesEntity.getId(),
                prescriptionMedicinesEntity.getPrescriptionId(),
                prescriptionMedicinesEntity.getProductNDC());
    }

}
