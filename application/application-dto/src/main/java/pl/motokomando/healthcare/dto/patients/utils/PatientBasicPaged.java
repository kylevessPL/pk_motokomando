package pl.motokomando.healthcare.dto.patients.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PatientBasicPaged {

    @ApiModelProperty(value = "Patient ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "Patient first name", example = "James")
    private String firstName;
    @ApiModelProperty(value = "Patient last name", example = "Smith")
    private String lastName;

}
