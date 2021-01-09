package pl.motokomando.healthcare.domain.model.doctors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class DoctorBasic {

    private final Integer id;
    private final String firstName;
    private final String lastName;

}
