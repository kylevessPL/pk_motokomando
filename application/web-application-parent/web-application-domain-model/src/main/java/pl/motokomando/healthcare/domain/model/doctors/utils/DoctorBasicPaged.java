package pl.motokomando.healthcare.domain.model.doctors.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class DoctorBasicPaged {

    private final Integer id;
    private final String firstName;
    private final String lastName;

}
