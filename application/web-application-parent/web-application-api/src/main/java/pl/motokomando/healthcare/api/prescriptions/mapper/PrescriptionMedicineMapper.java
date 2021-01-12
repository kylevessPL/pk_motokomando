package pl.motokomando.healthcare.api.prescriptions.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionMedicineDeleteRequest;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionMedicineRequest;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineDeleteRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionMedicineRequestCommand;

@Mapper
public interface PrescriptionMedicineMapper {

    PrescriptionMedicineRequestCommand mapToCommand(PrescriptionMedicineRequest request);
    PrescriptionMedicineDeleteRequestCommand mapToCommand(PrescriptionMedicineDeleteRequest request);

}
