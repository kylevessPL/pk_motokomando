package pl.motokomando.healthcare.domain.model.patients.utils;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PatientRequestCommand {

    @Nullable
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Sex sex;
    private BloodType bloodType;
    private String streetName;
    private String houseNumber;
    private String zipCode;
    private String city;
    private DocumentType documentType;
    private String documentId;
    private String phoneNumber;

}
