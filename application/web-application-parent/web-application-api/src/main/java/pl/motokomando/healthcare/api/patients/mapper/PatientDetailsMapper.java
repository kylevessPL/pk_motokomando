package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.dto.patients.utils.PatientDetails;

@Mapper
public interface PatientDetailsMapper {

    PatientDetails map(pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails patientDetails);

}
