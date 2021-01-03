package pl.motokomando.healthcare.domain.model.patients.utils;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BloodType {

    O_POS("0+"),
    O_NEG("0-"),
    A_POS("A+"),
    A_NEG("A-"),
    B_POS("B+"),
    B_NEG("B-"),
    AB_POS("AB+"),
    AB_NEG("AB-");

    @JsonValue
    private final String bloodType;

}
