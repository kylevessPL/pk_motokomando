package pl.motokomando.healthcare.domain.model.patients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.patients.utils.BloodType;
import pl.motokomando.healthcare.domain.model.patients.utils.DocumentType;

import java.sql.Date;

@RequiredArgsConstructor
@Getter
public final class Patient {

    private final String firstName;
    private final String lastName;
    private final Date birthDate;
    private final String sex;
    private final BloodType bloodType;
    private final String streetName;
    private final String houseNumber;
    private final String zipCode;
    private final String city;
    private final DocumentType documentType;
    private final String documentId;
    private final String phoneNumber;

}
