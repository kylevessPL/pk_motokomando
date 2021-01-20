package pl.motokomando.healthcare.dto.doctors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.doctors.utils.MedicalSpecialty;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class DoctorResponse {

    @Schema(description = "Doctor ID", example = "1")
    private Integer id;
    @Schema(description = "Doctor first name", example = "James")
    private String firstName;
    @Schema(description = "Doctor last name", example = "Smith")
    private String lastName;
    @Schema(description = "Doctor medical specialty")
    private MedicalSpecialty specialty;
    @Schema(description = "Doctor phone number", example = "+48502672107")
    private String phoneNumber;

}
