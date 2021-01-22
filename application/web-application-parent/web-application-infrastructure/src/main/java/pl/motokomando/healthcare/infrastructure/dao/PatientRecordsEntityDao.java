package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

import java.util.Optional;

public interface PatientRecordsEntityDao extends JpaRepository<PatientRecordsEntity, Integer> {

    Optional<PatientRecordsEntity> findByPatientId(Integer patientId);
    PatientRecordsEntity getByPatientId(Integer patientId);

}
