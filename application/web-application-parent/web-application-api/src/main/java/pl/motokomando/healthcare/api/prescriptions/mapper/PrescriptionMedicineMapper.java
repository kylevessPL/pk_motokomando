package pl.motokomando.healthcare.api.prescriptions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionMedicineDeleteRequest;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionMedicineRequest;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineRequestCommand;

@Mapper
public interface PrescriptionMedicineMapper {

    PrescriptionMedicineRequestCommand mapToCommand(PrescriptionMedicineRequest request);
    @Mapping(source = "id", target = "prescriptionId")
    PrescriptionMedicineDeleteRequestCommand mapToCommand(PrescriptionMedicineDeleteRequest request);

}
