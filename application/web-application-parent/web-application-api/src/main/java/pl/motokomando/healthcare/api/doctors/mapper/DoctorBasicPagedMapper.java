package pl.motokomando.healthcare.api.doctors.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorBasicPaged;
import pl.motokomando.healthcare.dto.doctors.DoctorBasicPagedResponse;

@Mapper
public interface DoctorBasicPagedMapper {

    DoctorBasicPagedResponse map(DoctorBasicPaged doctorBasicPaged);

}
