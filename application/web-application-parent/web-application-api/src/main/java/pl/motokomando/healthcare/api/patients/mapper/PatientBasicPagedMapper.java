package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasicPaged;

@Mapper
public interface PatientBasicPagedMapper {

    PatientBasicPaged map(pl.motokomando.healthcare.domain.model.patients.utils.PatientBasicPaged patientBasicPaged);

}
