package pl.motokomando.healthcare.api.doctors.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.doctors.utils.DoctorPagedQuery;
import pl.motokomando.healthcare.api.doctors.utils.DoctorRequest;
import pl.motokomando.healthcare.api.mapper.PageMetaMapper;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.dto.doctors.DoctorBasicPageResponse;
import pl.motokomando.healthcare.dto.doctors.DoctorResponse;

@Mapper(uses = { DoctorBasicPagedMapper.class, PageMetaMapper.class })
public interface DoctorsMapper {

    BasicPagedQueryCommand mapToCommand(DoctorPagedQuery query);
    DoctorRequestCommand mapToCommand(DoctorRequest request);
    DoctorBasicPageResponse mapToResponse(DoctorBasicPage doctorBasicPage);
    DoctorResponse mapToResponse(Doctor doctor);

}
