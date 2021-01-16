package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patients.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientBasicInfo;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

import java.util.Optional;

@Component
public class PatientRecordsEntityMapper {

    public Optional<PatientRecord> mapToPatientRecord(Optional<PatientRecordsEntity> patientRecordsEntity) {
        return patientRecordsEntity.map(this::createPatientRecord);
    }

    private PatientRecord createPatientRecord(PatientRecordsEntity patientRecordsEntity) {
        return new PatientRecord(
                patientRecordsEntity.getId(),
                patientRecordsEntity.getPatientId(),
                patientRecordsEntity.getHealthStatus(),
                patientRecordsEntity.getNotes(),
                patientRecordsEntity.getRegistrationDate().toLocalDateTime());
    }

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
