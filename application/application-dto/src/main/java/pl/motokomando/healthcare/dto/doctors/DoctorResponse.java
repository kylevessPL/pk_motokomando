package pl.motokomando.healthcare.dto.doctors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.doctors.utils.AcademicTitle;
import pl.motokomando.healthcare.dto.doctors.utils.MedicalSpecialty;

import java.util.List;

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
    @Schema(description = "Doctor academic title", example = "MD")
    private AcademicTitle academicTitle;
    @Schema(description = "Doctor medical specialties", example = "[\"ANESTHESIA\", \"PEDIATRIC\"]")
    private List<MedicalSpecialty> specialties;
    @Schema(description = "Doctor phone number", example = "+48502672107")
    private String phoneNumber;

}
