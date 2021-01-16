package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pl.motokomando.healthcare.api.patients.utils.PatientRecordPatchRequest;
import pl.motokomando.healthcare.domain.model.patients.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.dto.patients.PatientRecordResponse;

@Mapper
public interface PatientRecordsMapper {

    PatientRecordResponse mapToResponse(PatientRecord patient);
    PatientRecordPatchRequest mapToRequest(PatientRecordResponse response);
    PatientRecordPatchRequestCommand mapToCommand(PatientRecordResponse request);
    void update(PatientRecordPatchRequest request, @MappingTarget PatientRecordPatchRequestCommand command);

}
