package pl.motokomando.healthcare.api.prescriptions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.motokomando.healthcare.api.prescriptions.medicines.mapper.PrescriptionMedicineMapper;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionRequest;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.PrescriptionFull;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.dto.prescriptions.PrescriptionFullResponse;
import pl.motokomando.healthcare.dto.prescriptions.PrescriptionResponse;

@Mapper(uses = PrescriptionMedicineMapper.class)
public interface PrescriptionMapper {

    PrescriptionResponse mapToResponse(Prescription prescription);
    PrescriptionFullResponse mapToResponse(PrescriptionFull prescriptionFull);
    PrescriptionRequest mapToRequest(PrescriptionResponse response);
    PrescriptionRequestCommand mapToCommand(PrescriptionRequest request);
    PrescriptionPatchRequestCommand mapToCommand(PrescriptionResponse response);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "issueDate", ignore = true)
    void update(PrescriptionRequest request, @MappingTarget PrescriptionPatchRequestCommand command);

}
