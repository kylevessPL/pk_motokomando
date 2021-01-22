package pl.motokomando.healthcare.api.patients.utils;

import com.sun.istack.internal.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.patients.utils.BloodType;
import pl.motokomando.healthcare.domain.model.patients.utils.DocumentType;
import pl.motokomando.healthcare.domain.model.patients.utils.Sex;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientRequest implements Serializable {

    @Schema(description = "Patient ID for edit details purpose", example = "1")
    @Min(value = 1, message = "Patient ID must be a positive integer value")
    @Nullable
    private Integer id;
    @Schema(description = "Patient first name", example = "James")
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters long")
    private String firstName;
    @Schema(description = "Patient last name", example = "Smith")
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters long")
    private String lastName;
    @Schema(description = "Patient birth date")
    @NotNull(message = "Birth date is mandatory")
    private LocalDate birthDate;
    @Schema(description = "Patient sex", allowableValues = "MALE, FEMALE")
    @NotNull(message = "Sex is mandatory")
    private Sex sex;
    @Schema(description = "Patient blood type", allowableValues = "0+, 0-, A+, A-, B+, B-, AB+, AB-")
    @NotNull(message = "Blood type is mandatory")
    private BloodType bloodType;
    @Schema(description = "Patient street name", example = "Ethels Lane")
    @NotBlank(message = "Street name is mandatory")
    @Size(min = 2, max = 30, message = "Street name must be between 2 and 30 characters long")
    private String streetName;
    @Schema(description = "Patient house number", example = "747")
    @NotBlank(message = "House number is mandatory")
    @Pattern(regexp = "^[0-9a-zA-Z ./]*$", message = "House number must contain only letters and numbers")
    @Size(max = 10, message = "House number must be maximum 10 characters long")
    private String houseNumber;
    @Schema(description = "Patient zip code", example = "11735")
    @NotBlank(message = "Zip code is mandatory")
    @Pattern(regexp = "^[0-9 \\-]*$", message = "Zip code must contain only numbers, spaces or a dash")
    private String zipCode;
    @Schema(description = "Patient city", example = "Farmingdale")
    @NotBlank(message = "City is mandatory")
    @Size(min = 2, max = 30, message = "City must be between 2 and 30 characters long")
    private String city;
    @Schema(description = "Patient document type", allowableValues = "IDCARD, PASSPORT")
    @NotNull(message = "Document type is mandatory")
    private DocumentType documentType;
    @Schema(description = "Patient document ID", example = "SB1565402")
    @NotBlank(message = "Document id is mandatory")
    @Pattern(regexp = "^[A-Z0-9 -]*$", message = "Document ID must contain only capital letters, digits or a dash")
    @Size(min = 7, max = 14, message = "Document ID must be between 7 and 14 characters long")
    private String documentId;
    @Schema(description = "Patient phone number", example = "+48502672107")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+[- 0-9]+$", message = "Phone number must start with country code and contain only numbers, spaces or dashes")
    @Size(max = 16, message = "Phone number must be maximum 16 characters long")
    private String phoneNumber;

}
