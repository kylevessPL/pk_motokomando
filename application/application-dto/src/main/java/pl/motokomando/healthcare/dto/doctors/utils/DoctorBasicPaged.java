package pl.motokomando.healthcare.dto.doctors.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class DoctorBasicPaged {

    @ApiModelProperty(value = "Doctor ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "Doctor first name", example = "James")
    private String firstName;
    @ApiModelProperty(value = "Doctor last name", example = "Smith")
    private String lastName;

}
