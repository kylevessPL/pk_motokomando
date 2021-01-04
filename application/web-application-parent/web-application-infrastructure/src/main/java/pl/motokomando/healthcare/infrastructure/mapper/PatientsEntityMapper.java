package pl.motokomando.healthcare.infrastructure.mapper;

import pl.motokomando.healthcare.domain.model.patients.Patient;
import pl.motokomando.healthcare.domain.model.patients.PatientBasic;
import pl.motokomando.healthcare.infrastructure.model.PatientsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientsEntityMapper {

    public Optional<Patient> mapToPatient(Optional<PatientsEntity> patientsEntity) {
        return patientsEntity.map(this::createPatient);
    }

    public List<PatientBasic> mapToPatientBasicList(List<PatientsEntity> patientsEntityList) {
        return patientsEntityList
                .stream()
                .map(this::createPatientBasic)
                .collect(Collectors.toList());
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
