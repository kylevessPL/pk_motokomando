package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public final class AddPatientDetails implements Serializable {

    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final Sex sex;
    private final BloodType bloodType;
    private final String streetName;
    private final String houseNumber;
    private final String zipCode;
    private final String city;
    private final BigDecimal pesel;
    private final String phoneNumber;

}
