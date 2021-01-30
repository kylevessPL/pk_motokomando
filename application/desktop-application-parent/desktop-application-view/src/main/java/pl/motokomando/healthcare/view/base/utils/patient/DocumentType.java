package pl.motokomando.healthcare.view.base.utils.patient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public enum DocumentType {

    ID_CARD("Dowód osobisty"),
    PASSPORT("Paszport");

    private final String name;
}
