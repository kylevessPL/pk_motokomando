package pl.motokomando.healthcare.domain.model.doctors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.doctors.utils.MedicalSpecialty;

@RequiredArgsConstructor
@Getter
public final class Doctor {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final MedicalSpecialty specialty;
    private final String phoneNumber;

}
