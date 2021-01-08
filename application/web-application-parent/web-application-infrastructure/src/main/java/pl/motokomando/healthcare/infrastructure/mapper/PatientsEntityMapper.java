package pl.motokomando.healthcare.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasic;
import pl.motokomando.healthcare.domain.model.patients.PatientBasicPage;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;
import pl.motokomando.healthcare.infrastructure.model.PatientsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PatientsEntityMapper {

    public Optional<Patient> mapToPatient(Optional<PatientsEntity> patientsEntity) {
        return patientsEntity.map(this::createPatient);
    }

    public PatientBasicPage mapToPatientBasicPage(List<PatientsEntity> patientsEntityList, boolean isFirst, boolean isLast, boolean hasPrev, boolean hasNext, Integer currentPage, Integer totalPage, Long totalCount) {
        List<PatientBasic> patientBasicList = patientsEntityList
                .stream()
                .map(this::createPatientBasic)
                .collect(Collectors.toList());
        return new PatientBasicPage(
                new PageMeta(isFirst, isLast, hasPrev, hasNext, currentPage, totalPage, totalCount),
                patientBasicList);
    }

    private PatientBasic createPatientBasic(PatientsEntity patientsEntity) {
        return new PatientBasic(
                patientsEntity.getId(),
                patientsEntity.getFirstName(),
                patientsEntity.getLastName());
    }

    private Patient createPatient(PatientsEntity patientsEntity) {
        return new Patient(
                patientsEntity.getFirstName(),
                patientsEntity.getLastName(),
                patientsEntity.getBirthDate(),
                patientsEntity.getSex(),
                patientsEntity.getBloodType(),
                patientsEntity.getStreetName(),
                patientsEntity.getHouseNumber(),
                patientsEntity.getZipCode(),
                patientsEntity.getCity(),
                patientsEntity.getDocumentType(),
                patientsEntity.getDocumentId(),
                patientsEntity.getPhoneNumber());
    }

}
