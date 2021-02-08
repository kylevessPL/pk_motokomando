package pl.motokomando.healthcare.model.authentication.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthenticationStatus {

    NOT_AUTHENTICATED("Nie zalogowany"),
    AUTHENTICATION_STARTED("Logowanie..."),
    AUTHENTICATION_FAILURE("Logowanie nieudane"),
    USER_IDENTIFICATION("Pobieranie informacji o użytkowniku..."),
    AUTHENTICATION_SUCCESS("Zalogowano pomyślnie!");

    private final String description;

}
