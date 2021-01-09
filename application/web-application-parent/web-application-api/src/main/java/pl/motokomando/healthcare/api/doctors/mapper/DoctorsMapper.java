package pl.motokomando.healthcare.api.doctors.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.doctors.utils.DoctorQuery;
import pl.motokomando.healthcare.api.doctors.utils.DoctorRequest;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicQueryCommand;
import pl.motokomando.healthcare.dto.doctors.DoctorBasicResponse;
import pl.motokomando.healthcare.dto.doctors.DoctorResponse;

@Mapper(uses = DoctorBasicMapper.class)
public interface DoctorsMapper {

    BasicQueryCommand mapToCommand(DoctorQuery query);
    DoctorRequestCommand mapToCommand(DoctorRequest request);
    DoctorBasicResponse mapToResponse(DoctorBasicPage doctorBasicPage);
    DoctorResponse mapToResponse(Doctor doctor);

}
