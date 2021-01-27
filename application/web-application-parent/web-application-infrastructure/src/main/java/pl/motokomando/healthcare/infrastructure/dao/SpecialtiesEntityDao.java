package pl.motokomando.healthcare.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.motokomando.healthcare.domain.model.doctors.utils.MedicalSpecialty;
import pl.motokomando.healthcare.infrastructure.model.SpecialtiesEntity;

import java.util.List;
import java.util.Set;

public interface SpecialtiesEntityDao extends JpaRepository<SpecialtiesEntity, Integer> {

    Set<SpecialtiesEntity> getAllBySpecialtyIn(List<MedicalSpecialty> specialties);

}
