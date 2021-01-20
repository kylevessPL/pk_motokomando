package pl.motokomando.healthcare.api.prescriptions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.motokomando.healthcare.api.prescriptions.utils.PrescriptionRequest;
import pl.motokomando.healthcare.domain.model.prescriptions.Prescription;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionPatchRequestCommand;
import pl.motokomando.healthcare.domain.model.prescriptions.utils.PrescriptionRequestCommand;
import pl.motokomando.healthcare.dto.prescriptions.PrescriptionResponse;

@Mapper
public interface PrescriptionMapper {

    PrescriptionResponse mapToResponse(Prescription prescription);
    PrescriptionRequest mapToRequest(PrescriptionResponse response);
    PrescriptionRequestCommand mapToCommand(PrescriptionRequest request);
    PrescriptionPatchRequestCommand mapToCommand(PrescriptionResponse response);
    void update(PrescriptionRequest request, @MappingTarget PrescriptionPatchRequestCommand command);

}
