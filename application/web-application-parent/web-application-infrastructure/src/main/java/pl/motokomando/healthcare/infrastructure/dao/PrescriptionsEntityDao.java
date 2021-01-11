package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.PrescriptionsEntity;

public interface PrescriptionsEntityDao extends JpaRepository<PrescriptionsEntity, Integer> {
}
