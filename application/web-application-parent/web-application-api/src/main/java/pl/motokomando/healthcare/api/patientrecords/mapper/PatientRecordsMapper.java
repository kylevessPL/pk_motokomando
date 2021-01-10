package pl.motokomando.healthcare.api.patientrecords.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.patientrecords.PatientRecord;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientRecordRequestCommand;
import pl.motokomando.healthcare.dto.patientrecords.PatientRecordResponse;

@Mapper
public interface PatientRecordsMapper {

    PatientRecordResponse mapToResponse(PatientRecord patient);
    PatientRecordRequestCommand mapToCommand(PatientRecordResponse response);

}
