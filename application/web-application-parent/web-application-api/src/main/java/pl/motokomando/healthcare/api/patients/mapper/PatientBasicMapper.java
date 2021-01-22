package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientBasicInfo;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails;
import pl.motokomando.healthcare.dto.patients.PatientDetailsResponse;
import pl.motokomando.healthcare.dto.patients.records.PatientBasicInfoResponse;

@Mapper
public interface PatientBasicMapper {

    PatientBasicInfoResponse map(PatientBasicInfo patientBasicInfo);
    PatientDetailsResponse map(PatientDetails patientDetails);

}
