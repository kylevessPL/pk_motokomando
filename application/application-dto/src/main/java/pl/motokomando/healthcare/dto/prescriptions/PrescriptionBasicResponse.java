package pl.motokomando.healthcare.dto.prescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionBasicResponse {

    @ApiModelProperty(value = "Prescription ID", example = "1")
    private Integer id;

}
