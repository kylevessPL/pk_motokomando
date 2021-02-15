package pl.motokomando.healthcare.api.patients.utils;

import com.sun.istack.internal.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.motokomando.healthcare.domain.model.patients.utils.BloodType;
import pl.motokomando.healthcare.domain.model.patients.utils.Sex;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

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
    @DateTimeFormat(iso = DATE)
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    @Schema(description = "Patient sex", example = "MALE")
    @NotNull(message = "Sex is mandatory")
    private Sex sex;
    @Schema(description = "Patient blood type", example = "AB_NEG")
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
    @Size(max = 10, message = "Zip code must be maximum 10 characters long")
    private String zipCode;
    @Schema(description = "Patient city", example = "Farmingdale")
    @NotBlank(message = "City is mandatory")
    @Size(min = 2, max = 30, message = "City must be between 2 and 30 characters long")
    private String city;
    @Schema(description = "Patient PESEL number", example = "12310188859")
    @NotNull(message = "PESEL number is mandatory")
    @DecimalMin(value = "10000000000.0", inclusive = false, message = "PESEL must be 11 digits long")
    @Digits(integer = 11, fraction = 0, message = "Not a valid PESEL number")
    private BigDecimal pesel;
    @Schema(description = "Patient phone number", example = "+48502672107")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must start with country code and contain only numbers, spaces or dashes")
    @Size(min = 7, max = 15, message = "Phone number must be between 7 and 16 characters long")
    private String phoneNumber;

}
