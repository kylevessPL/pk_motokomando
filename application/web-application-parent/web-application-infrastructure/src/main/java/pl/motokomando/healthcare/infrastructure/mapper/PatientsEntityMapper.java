package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientBasicPaged;
import pl.motokomando.healthcare.domain.model.patients.utils.PatientDetails;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;
import pl.motokomando.healthcare.infrastructure.model.PatientsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PatientsEntityMapper {

    public Optional<PatientDetails> mapToPatient(Optional<PatientsEntity> patientsEntity) {
        return patientsEntity.map(this::createPatientDetails);
    }

    public PatientBasicPage mapToPatientBasicPage(List<PatientsEntity> patientsEntityList, boolean isFirst, boolean isLast, boolean hasPrev, boolean hasNext, Integer currentPage, Integer totalPage, Long totalCount) {
        List<PatientBasicPaged> patientBasicPagedList = patientsEntityList
                .stream()
                .map(this::createPatientBasicPaged)
                .collect(Collectors.toList());
        return new PatientBasicPage(
                new PageMeta(isFirst, isLast, hasPrev, hasNext, currentPage, totalPage, totalCount),
                patientBasicPagedList);
    }

    private PatientBasicPaged createPatientBasicPaged(PatientsEntity patientsEntity) {
        return new PatientBasicPaged(
                patientsEntity.getId(),
                patientsEntity.getFirstName(),
                patientsEntity.getLastName());
    }

    private PatientDetails createPatientDetails(PatientsEntity patientsEntity) {
        return new PatientDetails(
                patientsEntity.getFirstName(),
                patientsEntity.getLastName(),
                patientsEntity.getBirthDate(),
                patientsEntity.getSex(),
                patientsEntity.getBloodType(),
                patientsEntity.getStreetName(),
                patientsEntity.getHouseNumber(),
                patientsEntity.getZipCode(),
                patientsEntity.getCity(),
                patientsEntity.getPesel(),
                patientsEntity.getPhoneNumber());
    }

}
