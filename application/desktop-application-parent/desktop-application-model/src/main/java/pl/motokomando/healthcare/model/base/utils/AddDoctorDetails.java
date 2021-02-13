package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
@Getter
public final class AddDoctorDetails implements Serializable {

    private final String firstName;
    private final String lastName;
    private final AcademicTitle academicTitle;
    private final List<MedicalSpecialty> specialties;
    private final String phoneNumber;

}
