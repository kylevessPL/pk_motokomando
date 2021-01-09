package pl.motokomando.healthcare.api.doctors.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.mapper.PageMetaMapper;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.dto.doctors.utils.DoctorBasic;

@Mapper(uses = PageMetaMapper.class)
public interface DoctorBasicMapper {

    DoctorBasic map(DoctorBasicPage doctorBasicPage);

}
