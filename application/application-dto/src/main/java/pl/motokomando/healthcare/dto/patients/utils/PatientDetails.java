package pl.motokomando.healthcare.dto.patients.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientDetails {

    @ApiModelProperty(value = "Patient first name", example = "James")
    private String firstName;
    @ApiModelProperty(value = "Patient last name", example = "Smith")
    private String lastName;
    @ApiModelProperty(value = "Patient birth date", example = "01-01-1985")
    private LocalDate birthDate;
    @ApiModelProperty(value = "Patient sex", allowableValues = "MALE, FEMALE")
    private Sex sex;
    @ApiModelProperty(value = "Patient blood type", allowableValues = "0+, 0-, A+, A-, B+, B-, AB+, AB-")
    private BloodType bloodType;
    @ApiModelProperty(value = "Patient street name", example = "Ethels Lane")
    private String streetName;
    @ApiModelProperty(value = "Patient house number", example = "747")
    private String houseNumber;
    @ApiModelProperty(value = "Patient zip code", example = "11735")
    private String zipCode;
    @ApiModelProperty(value = "Patient city", example = "Farmingdale")
    private String city;
    @ApiModelProperty(value = "Patient document type", allowableValues = "IDCARD, PASSPORT")
    private DocumentType documentType;
    @ApiModelProperty(value = "Patient document ID", example = "SB1565402")
    private String documentId;
    @ApiModelProperty(value = "Patient phone number", example = "+48502672107")
    private String phoneNumber;

}
