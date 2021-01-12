package pl.motokomando.healthcare.api.prescriptions.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineRequest {

    @ApiModelProperty(value = "Medicine NDC", example = "0536-1261")
    @NotBlank(message = "Product NDC is mandatory")
    private String productNDC;

}
