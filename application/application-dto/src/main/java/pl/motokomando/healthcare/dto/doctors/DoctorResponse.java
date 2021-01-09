package pl.motokomando.healthcare.dto.doctors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.doctors.utils.MedicalSpecialty;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class DoctorResponse {

    @ApiModelProperty(value = "Doctor first name", example = "James")
    private String firstName;
    @ApiModelProperty(value = "Doctor last name", example = "Smith")
    private String lastName;
    @ApiModelProperty(value = "Doctor medical specialty")
    private MedicalSpecialty specialty;
    @ApiModelProperty(value = "Doctor phone number", example = "+48502672107")
    private String phoneNumber;

}
