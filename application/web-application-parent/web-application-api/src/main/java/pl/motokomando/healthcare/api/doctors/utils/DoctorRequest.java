package pl.motokomando.healthcare.api.doctors.utils;

import com.sun.istack.internal.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.doctors.utils.MedicalSpecialty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class DoctorRequest implements Serializable {

    @Schema(description = "Doctor ID for edit details purpose", example = "1")
    @Min(value = 1, message = "Doctor ID must be a positive integer value")
    @Nullable
    private Integer id;
    @Schema(description = "Doctor first name", example = "James")
    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters long")
    private String firstName;
    @Schema(description = "Doctor last name", example = "Smith")
    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters long")
    private String lastName;
    @Schema(description = "Doctor medical specialty")
    @NotNull(message = "Medical specialty is mandatory")
    private MedicalSpecialty specialty;
    @Schema(description = "Doctor phone number", example = "+48502672107")
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+[- 0-9]+$", message = "Phone number must start with country code and contain only numbers, spaces or dashes")
    @Size(max = 16, message = "Phone number must be maximum 16 characters long")
    private String phoneNumber;

}
