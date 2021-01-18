package pl.motokomando.healthcare.dto.patients.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientDetails {

    @Schema(description = "Patient first name", example = "James")
    private String firstName;
    @Schema(description = "Patient last name", example = "Smith")
    private String lastName;
    @Schema(description = "Patient birth date", example = "01-01-1985")
    private LocalDate birthDate;
    @Schema(description = "Patient sex", allowableValues = "MALE, FEMALE")
    private Sex sex;
    @Schema(description = "Patient blood type", allowableValues = "0+, 0-, A+, A-, B+, B-, AB+, AB-")
    private BloodType bloodType;
    @Schema(description = "Patient street name", example = "Ethels Lane")
    private String streetName;
    @Schema(description = "Patient house number", example = "747")
    private String houseNumber;
    @Schema(description = "Patient zip code", example = "11735")
    private String zipCode;
    @Schema(description = "Patient city", example = "Farmingdale")
    private String city;
    @Schema(description = "Patient document type", allowableValues = "IDCARD, PASSPORT")
    private DocumentType documentType;
    @Schema(description = "Patient document ID", example = "SB1565402")
    private String documentId;
    @Schema(description = "Patient phone number", example = "+48502672107")
    private String phoneNumber;

}
