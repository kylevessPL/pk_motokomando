package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientBasicPaged;
import pl.motokomando.healthcare.dto.patients.PatientBasicPagedResponse;

@Mapper
public interface PatientBasicPagedMapper {

    PatientBasicPagedResponse map(PatientBasicPaged patientBasicPaged);

}
