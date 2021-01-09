package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patientrecords.utils.PatientBasicInfo;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

@Component
public class PatientRecordsEntityMapper {

    public PatientBasicInfo mapToPatientBasicInfo(PatientRecordsEntity patientRecordsEntity) {
        return createPatientBasicInfo(patientRecordsEntity);
    }

    private PatientBasicInfo createPatientBasicInfo(PatientRecordsEntity patientRecordsEntity) {
        return new PatientBasicInfo(
                patientRecordsEntity.getPatientId(),
                patientRecordsEntity.getId(),
                patientRecordsEntity.getRegistrationDate().toLocalDateTime());
    }

}
