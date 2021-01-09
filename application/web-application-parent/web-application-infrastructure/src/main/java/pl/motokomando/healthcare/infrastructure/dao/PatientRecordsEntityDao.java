package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PatientRecordsEntity;

public interface PatientRecordsEntityDao extends JpaRepository<PatientRecordsEntity, Integer> {

    PatientRecordsEntity getByPatientId(Integer patientId);

}
