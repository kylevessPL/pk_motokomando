package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasicInfo;

@Mapper
public interface PatientBasicInfoMapper {

    PatientBasicInfo map(pl.motokomando.healthcare.domain.model.patients.utils.PatientBasicInfo patientBasicInfo);

}
