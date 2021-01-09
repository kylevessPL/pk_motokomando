package pl.motokomando.healthcare.api.patientrecords.utils;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.dto.patientrecords.utils.PatientBasicInfo;

@Mapper
public interface PatientBasicInfoMapper {

    PatientBasicInfo map(pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientBasicInfo patientBasicInfo);

}
