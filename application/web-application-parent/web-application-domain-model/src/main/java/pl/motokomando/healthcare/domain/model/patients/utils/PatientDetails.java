package pl.motokomando.healthcare.domain.model.patients.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public final class PatientDetails {

    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final Sex sex;
    private final BloodType bloodType;
    private final String streetName;
    private final String houseNumber;
    private final String zipCode;
    private final String city;
    private final DocumentType documentType;
    private final String documentId;
    private final String phoneNumber;

}
