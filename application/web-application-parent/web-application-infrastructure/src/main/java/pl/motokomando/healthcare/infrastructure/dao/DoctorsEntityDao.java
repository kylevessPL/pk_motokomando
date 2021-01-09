package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.infrastructure.model.DoctorsEntity;

public interface DoctorsEntityDao extends JpaRepository<DoctorsEntity, Integer> {
}
