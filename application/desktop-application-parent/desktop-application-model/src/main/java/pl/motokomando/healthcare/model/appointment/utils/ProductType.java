package pl.motokomando.healthcare.model.appointment.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    PRESCRIPTION ("Lek na receptę"),
    OTC("Lek bez recepty"),
    PROCESSING("Lek do przetwarzania");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }

}
