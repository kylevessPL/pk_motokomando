package pl.motokomando.healthcare.api.patients.records.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.motokomando.healthcare.api.patients.records.utils.PatientRecordPatchRequest;
import pl.motokomando.healthcare.domain.model.patients.records.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.dto.patients.records.PatientRecordResponse;

@Mapper
public interface PatientRecordsMapper {

    PatientRecordResponse mapToResponse(PatientRecord patient);
    PatientRecordPatchRequest mapToRequest(PatientRecordResponse response);
    PatientRecordPatchRequestCommand mapToCommand(PatientRecordResponse request);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patientId", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    void update(PatientRecordPatchRequest request, @MappingTarget PatientRecordPatchRequestCommand command);

}
