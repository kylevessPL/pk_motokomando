package pl.motokomando.healthcare.domain.model.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NO_MEDICINES_FOUND("MS04", "Nie znaleziono leków pasujących do zapytania"),
    MEDICINE_NOT_FOUND("M04", "Nie znaleziono leku o podanym NDC"),
    PATIENT_NOT_FOUND("P04", "Nie znaleziono pacjenta o tym ID"),
    PATIENT_RECORD_NOT_FOUND("PR04", "Nie znaleziono karty pacjenta o tym ID"),
    BILL_NOT_FOUND("B04", "Nie znaleziono rachunku o tym ID"),
    PRESCRIPTION_NOT_FOUND("PS04", "Nie znaleziono recepty o tym ID"),
    PRESCRIPTION_MEDICINE_NOT_FOUND("PM04", "Nie znaleziono leku o tym ID"),
    PRESCRIPTION_MEDICINE_ALREADY_EXISTS("PM09", "Podany lek już istnieje na recepcie"),
    DOCTOR_NOT_FOUND("D04", "Nie znaleziono lekarza o tym ID"),
    APPOINTMENT_NOT_FOUND("AP04", "Nie znaleziono wizyty lekarskiej o tym ID"),
    DATE_NOT_AVAILABLE("AP09", "Podana data jest zajęta"),
    VALIDATION_FAILED("V06", "Podane dane są niepoprawne");

    private final String code;
    private final String message;

}
