package pl.motokomando.healthcare.api.prescriptions.medicines.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineBasicRequest;
import pl.motokomando.healthcare.api.prescriptions.medicines.utils.PrescriptionMedicineRequest;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.PrescriptionMedicine;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineBasicRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.medicines.utils.PrescriptionMedicineRequestCommand;
import pl.motokomando.healthcare.dto.prescriptions.medicines.PrescriptionMedicineResponse;

@Mapper
public interface PrescriptionMedicineMapper {

    PrescriptionMedicineRequestCommand mapToCommand(PrescriptionMedicineRequest request);
    PrescriptionMedicineBasicRequestCommand mapToCommand(PrescriptionMedicineBasicRequest request);
    PrescriptionMedicineResponse mapToResponse(PrescriptionMedicine prescription);

}
