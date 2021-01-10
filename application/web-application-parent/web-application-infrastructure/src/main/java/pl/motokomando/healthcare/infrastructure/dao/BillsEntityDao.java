package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.BillsEntity;

public interface BillsEntityDao extends JpaRepository<BillsEntity, Integer> {
}
