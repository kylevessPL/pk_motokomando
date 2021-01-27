package pl.motokomando.healthcare.domain.model.doctors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.domain.model.doctors.utils.MedicalSpecialty;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class Doctor {

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final List<MedicalSpecialty> specialties;
    private final String phoneNumber;

}
