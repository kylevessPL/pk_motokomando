package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.mapper.PageMetaMapper;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.dto.patients.utils.PatientBasic;

@Mapper(uses = PageMetaMapper.class)
public interface PatientBasicMapper {

    PatientBasic map(PatientBasicPage patientBasicPage);

}
