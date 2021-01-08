package pl.motokomando.healthcare.api.patients.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.api.patients.utils.PatientQuery;
import pl.motokomando.healthcare.api.patients.utils.PatientRequest;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientQueryCommand;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientRequestCommand;
import pl.motokomando.healthcare.dto.patients.PatientBasicResponse;
import pl.motokomando.healthcare.dto.patients.PatientResponse;

@Mapper(uses = PatientBasicMapper.class)
public interface PatientsMapper {

    PatientQueryCommand mapToCommand(PatientQuery query);
    PatientRequestCommand mapToCommand(PatientRequest request);
    PatientBasicResponse mapToResponse(PatientBasicPage patientBasicPage);
    PatientResponse mapToResponse(Patient patient);

}
