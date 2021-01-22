package pl.motokomando.healthcare.domain.model.utils;

import lombok.Getter;

@Getter
public class NoMedicinesFoundException extends BasicException {

    private final ErrorCode errorCode;

    public NoMedicinesFoundException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

}
