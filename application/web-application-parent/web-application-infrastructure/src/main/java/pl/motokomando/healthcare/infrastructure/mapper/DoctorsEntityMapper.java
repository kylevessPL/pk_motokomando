package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.doctors.utils.DoctorBasicPaged;
import pl.motokomando.healthcare.domain.model.doctors.utils.MedicalSpecialty;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;
import pl.motokomando.healthcare.infrastructure.model.DoctorsEntity;
import pl.motokomando.healthcare.infrastructure.model.SpecialtiesEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DoctorsEntityMapper {

    public Doctor mapToDoctor(DoctorsEntity doctorsEntity) {
        return createDoctor(doctorsEntity);
    }

    public Optional<Doctor> mapToDoctor(Optional<DoctorsEntity> doctorsEntity) {
        return doctorsEntity.map(this::createDoctor);
    }

    public DoctorBasicPage mapToDoctorBasicPage(List<DoctorsEntity> doctorsEntityList, boolean isFirst, boolean isLast, boolean hasPrev, boolean hasNext, Integer currentPage, Integer totalPage, Long totalCount) {
        List<DoctorBasicPaged> doctorBasicPagedList = doctorsEntityList
                .stream()
                .map(this::createDoctorBasicPaged)
                .collect(Collectors.toList());
        return new DoctorBasicPage(
                new PageMeta(isFirst, isLast, hasPrev, hasNext, currentPage, totalPage, totalCount),
                doctorBasicPagedList);
    }

    private DoctorBasicPaged createDoctorBasicPaged(DoctorsEntity doctorsEntity) {
        return new DoctorBasicPaged(
                doctorsEntity.getId(),
                doctorsEntity.getFirstName(),
                doctorsEntity.getLastName());
    }

    private Doctor createDoctor(DoctorsEntity doctorsEntity) {
        List<MedicalSpecialty> specialties = doctorsEntity.getSpecialties()
                .stream()
                .map(SpecialtiesEntity::getSpecialty)
                .collect(Collectors.toList());
        return new Doctor(
                doctorsEntity.getId(),
                doctorsEntity.getFirstName(),
                doctorsEntity.getLastName(),
                specialties,
                doctorsEntity.getPhoneNumber());
    }

}
