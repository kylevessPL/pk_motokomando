package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Doctor {

    private final String firstName;
    private final String lastName;
    private final AcademicTitle academicTitle;
    private final List<MedicalSpecialty> specialties;
    private final String phoneNumber;

}
