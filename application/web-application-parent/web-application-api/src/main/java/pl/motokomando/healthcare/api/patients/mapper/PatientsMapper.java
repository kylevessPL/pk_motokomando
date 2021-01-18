package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.mapper.PageMetaMapper;
import pl.motokomando.healthcare.api.patientrecords.mapper.PatientBasicInfoMapper;
import pl.motokomando.healthcare.api.patients.utils.PatientPagedQuery;
import pl.motokomando.healthcare.api.patients.utils.PatientRequest;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.domain.model.utils.BasicPagedQueryCommand;
import pl.motokomando.healthcare.dto.patients.PatientBasicPageResponse;
import pl.motokomando.healthcare.dto.patients.PatientResponse;

@Mapper(uses = { PatientBasicPagedMapper.class, PageMetaMapper.class, PatientDetailsMapper.class, PatientBasicInfoMapper.class })
public interface PatientsMapper {

    BasicPagedQueryCommand mapToCommand(PatientPagedQuery query);
    PatientRequestCommand mapToCommand(PatientRequest request);
    PatientBasicPageResponse mapToResponse(PatientBasicPage patientBasicPage);
    PatientResponse mapToResponse(Patient patient);

}
