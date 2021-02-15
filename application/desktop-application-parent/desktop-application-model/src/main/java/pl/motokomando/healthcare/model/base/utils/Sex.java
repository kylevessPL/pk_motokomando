package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Sex {

    MALE("Mężczyzna"),
    FEMALE("Kobieta");

    private final String name;

    public static Sex findByName(String name) {
        return Arrays.stream(Sex.values())
                .filter(e -> e.name.equals(name))
                .findFirst()
                .orElse(null);
    }

}
