package pl.motokomando.healthcare.api.patients.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.motokomando.healthcare.dto.patients.utils.BloodType;
import pl.motokomando.healthcare.dto.patients.utils.DocumentType;
import pl.motokomando.healthcare.dto.patients.utils.Sex;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientRequest implements Serializable {

    @ApiModelProperty(value = "Patient first name", example = "James")
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters long")
    private String firstName;
    @ApiModelProperty(value = "Patient last name", example = "Smith")
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters long")
    private String lastName;
    @ApiModelProperty(value = "Patient birth date", example = "01-01-1985")
    @NotNull(message = "Birth date is mandatory")
    @Future(message = "Birth date must be in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    @ApiModelProperty(value = "Patient sex", allowableValues = "MALE, FEMALE")
    @NotNull(message = "Sex is mandatory")
    private Sex sex;
    @ApiModelProperty(value = "Patient blood type", allowableValues = "0+, 0-, A+, A-, B+, B-, AB+, AB-")
    @NotNull(message = "Blood type is mandatory")
    private BloodType bloodType;
    @ApiModelProperty(value = "Patient street name", example = "Ethels Lane")
    @NotBlank(message = "Street name is mandatory")
    @Size(min = 2, max = 30, message = "Street name must be between 2 and 30 characters long")
    private String streetName;
    @ApiModelProperty(value = "Patient house number", example = "747")
    @NotBlank(message = "House number is mandatory")
    @Pattern(regexp = "^[0-9a-zA-Z ./]*$", message = "House number must contain only letters and numbers")
    @Size(max = 10, message = "House number must be maximum 10 characters long")
    private String houseNumber;
    @ApiModelProperty(value = "Patient zip code", example = "11735")
    @NotBlank(message = "Zip code is mandatory")
    @Pattern(regexp = "^[0-9 \\-]*$", message = "Zip code must contain only numbers, spaces or a dash")
    private String zipCode;
    @ApiModelProperty(value = "Patient city", example = "Farmingdale")
    @NotBlank(message = "City is mandatory")
    @Size(min = 2, max = 30, message = "City must be between 2 and 30 characters long")
    private String city;
    @ApiModelProperty(value = "Patient document type", allowableValues = "IDCARD, PASSPORT")
    @NotNull(message = "Document type is mandatory")
    private DocumentType documentType;
    @ApiModelProperty(value = "Patient document ID", example = "SB1565402")
    @NotBlank(message = "Document id is mandatory")
    @Pattern(regexp = "^[A-Z0-9 -]*$", message = "Document ID must contain only capital letters, digits or a dash")
    @Size(min = 7, max = 14, message = "Document ID must be between 7 and 14 characters long")
    private String documentId;
    @ApiModelProperty(value = "Patient phone number", example = "+48502672107")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+[- 0-9]+$", message = "Phone number must start with country code and contain only numbers, spaces or dashes")
    @Size(max = 16, message = "Phone number must be maximum 16 characters long")
    private String phoneNumber;

}
