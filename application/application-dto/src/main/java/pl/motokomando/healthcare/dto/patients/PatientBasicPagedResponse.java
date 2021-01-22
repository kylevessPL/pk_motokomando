package pl.motokomando.healthcare.dto.patients;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PatientBasicPagedResponse {

    @Schema(description = "Patient ID", example = "1")
    private Integer id;
    @Schema(description = "Patient first name", example = "James")
    private String firstName;
    @Schema(description = "Patient last name", example = "Smith")
    private String lastName;

}
