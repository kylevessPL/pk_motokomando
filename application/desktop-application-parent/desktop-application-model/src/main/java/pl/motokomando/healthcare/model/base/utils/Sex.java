package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sex {

    MALE("Mężczyzna"),
    FEMALE("Kobieta");

    private final String name;

}
