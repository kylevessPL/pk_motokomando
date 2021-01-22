package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patients.records.CurrentHealth;
import pl.motokomando.healthcare.domain.model.patients.records.PatientRecord;
import pl.motokomando.healthcare.domain.model.patients.records.utils.PatientBasicInfo;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

import java.util.Optional;

@Component
public class PatientRecordsEntityMapper {

    public Optional<PatientRecord> mapToPatientRecord(Optional<PatientRecordsEntity> patientRecordsEntity) {
        return patientRecordsEntity.map(this::createPatientRecord);
    }

    public PatientBasicInfo mapToPatientBasicInfo(PatientRecordsEntity patientRecordsEntity) {
        return createPatientBasicInfo(patientRecordsEntity);
    }

    public Optional<CurrentHealth> mapToCurrentHealth(Optional<PatientRecordsEntity> patientRecordsEntity) {
        return patientRecordsEntity.map(this::createCurrentHealth);
    }

    private PatientRecord createPatientRecord(PatientRecordsEntity patientRecordsEntity) {
        return new PatientRecord(
                patientRecordsEntity.getId(),
                patientRecordsEntity.getPatientId(),
                patientRecordsEntity.getHealthStatus(),
                patientRecordsEntity.getNotes(),
                patientRecordsEntity.getRegistrationDate().toLocalDateTime());
    }

    private PatientBasicInfo createPatientBasicInfo(PatientRecordsEntity patientRecordsEntity) {
        return new PatientBasicInfo(
                patientRecordsEntity.getPatientId(),
                patientRecordsEntity.getRegistrationDate().toLocalDateTime());
    }

    private CurrentHealth createCurrentHealth(PatientRecordsEntity patientRecordsEntity) {
        return new CurrentHealth(
                patientRecordsEntity.getHealthStatus(),
                patientRecordsEntity.getNotes());
    }

}
