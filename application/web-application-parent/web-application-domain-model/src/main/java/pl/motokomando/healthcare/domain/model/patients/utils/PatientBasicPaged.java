package pl.motokomando.healthcare.domain.model.patients.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class PatientBasicPaged {

    private final Integer id;
    private final String firstName;
    private final String lastName;

}
