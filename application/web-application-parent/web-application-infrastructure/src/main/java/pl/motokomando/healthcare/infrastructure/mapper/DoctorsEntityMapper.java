package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.doctors.Doctor;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasic;
import pl.motokomando.healthcare.domain.model.doctors.DoctorBasicPage;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;
import pl.motokomando.healthcare.infrastructure.model.DoctorsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DoctorsEntityMapper {

    public Optional<Doctor> mapToDoctor(Optional<DoctorsEntity> doctorsEntity) {
        return doctorsEntity.map(this::createDoctor);
    }

    public DoctorBasicPage mapToDoctorBasicPage(List<DoctorsEntity> doctorsEntityList, boolean isFirst, boolean isLast, boolean hasPrev, boolean hasNext, Integer currentPage, Integer totalPage, Long totalCount) {
        List<DoctorBasic> doctorBasicList = doctorsEntityList
                .stream()
                .map(this::createDoctorBasic)
                .collect(Collectors.toList());
        return new DoctorBasicPage(
                new PageMeta(isFirst, isLast, hasPrev, hasNext, currentPage, totalPage, totalCount),
                doctorBasicList);
    }

    private DoctorBasic createDoctorBasic(DoctorsEntity doctorsEntity) {
        return new DoctorBasic(
                doctorsEntity.getId(),
                doctorsEntity.getFirstName(),
                doctorsEntity.getLastName());
    }

    private Doctor createDoctor(DoctorsEntity doctorsEntity) {
        return new Doctor(
                doctorsEntity.getFirstName(),
                doctorsEntity.getLastName(),
                doctorsEntity.getSpecialty(),
                doctorsEntity.getPhoneNumber());
    }

}