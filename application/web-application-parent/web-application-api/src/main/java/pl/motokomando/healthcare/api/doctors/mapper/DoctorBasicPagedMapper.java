package pl.motokomando.healthcare.api.doctors.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.dto.doctors.utils.DoctorBasicPaged;

@Mapper
public interface DoctorBasicPagedMapper {

    DoctorBasicPaged map(pl.motokomando.healthcare.domain.model.doctors.utils.DoctorBasicPaged doctorBasicPaged);

}
