package pl.motokomando.healthcare.api.patientrecords.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.patientrecords.utils.PatientRecordPatchRequest;
import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordPatchRequestCommand;
import pl.motokomando.healthcare.dto.patientrecords.PatientRecordResponse;

@Mapper
public interface PatientRecordsMapper {

    PatientRecordResponse mapToResponse(PatientRecord patient);
    PatientRecordPatchRequest mapToRequest(PatientRecordResponse response);
    PatientRecordPatchRequestCommand mapToCommand(PatientRecordPatchRequest request);

}
