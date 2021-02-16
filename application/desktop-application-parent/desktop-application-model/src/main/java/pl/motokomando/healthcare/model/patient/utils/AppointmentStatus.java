package pl.motokomando.healthcare.model.patient.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppointmentStatus {

    VALID("Umówiona"),
    CANCELLED("Anulowana");

    private final String name;

}
