package pl.motokomando.healthcare.domain.model.patients;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class PatientBasic {

    private final Integer id;
    private final String firstName;
    private final String lastName;

}
