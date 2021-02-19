package pl.motokomando.healthcare.model.appointment.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class ActiveIngredient {

    private final String name;
    private final String strength;

    @Override
    public String toString() {
        return String.format("%s, %s", name, strength);
    }

}
