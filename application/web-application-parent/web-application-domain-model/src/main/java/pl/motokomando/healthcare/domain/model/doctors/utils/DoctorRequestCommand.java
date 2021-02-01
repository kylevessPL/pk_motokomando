package pl.motokomando.healthcare.domain.model.doctors.utils;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DoctorRequestCommand {

    @Nullable
    private Integer id;
    private String firstName;
    private String lastName;
    private AcademicTitle academicTitle;
    private List<MedicalSpecialty> specialties;
    private String phoneNumber;

}
