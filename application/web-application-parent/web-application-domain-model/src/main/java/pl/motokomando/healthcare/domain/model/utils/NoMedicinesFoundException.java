package pl.motokomando.healthcare.domain.model.utils;

import lombok.Getter;

import static pl.motokomando.healthcare.domain.model.utils.ErrorCode.NO_MEDICINES_FOUND;

@Getter
public class NoMedicinesFoundException extends MyException {

    public NoMedicinesFoundException() {
        super(NO_MEDICINES_FOUND);
    }

}
