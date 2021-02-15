package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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

    private final String name;

    public static BloodType findByName(String name) {
        return Arrays.stream(BloodType.values())
                .filter(e -> e.name.equals(name))
                .findFirst()
                .orElse(null);
    }

}
