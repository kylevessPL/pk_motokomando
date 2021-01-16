package pl.motokomando.healthcare.api.prescriptions.medicines.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineDeleteRequest;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineRequest;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineRequestCommand;

@Mapper
public interface PrescriptionMedicineMapper {

    PrescriptionMedicineRequestCommand mapToCommand(PrescriptionMedicineRequest request);
    @Mapping(source = "id", target = "prescriptionId")
    PrescriptionMedicineDeleteRequestCommand mapToCommand(PrescriptionMedicineDeleteRequest request);

}
