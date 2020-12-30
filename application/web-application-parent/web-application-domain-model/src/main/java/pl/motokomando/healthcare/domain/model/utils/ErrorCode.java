package pl.motokomando.healthcare.domain.model.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NO_MEDICINES_FOUND("M04", "No medicines found matching your query"),
    VALIDATION_FAILED("V06", "Validation failed");

    private final String code;
    private final String message;

}
