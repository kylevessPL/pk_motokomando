package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PatientsEntity;

public interface PatientsEntityDao extends JpaRepository<PatientsEntity, Integer> {
}
