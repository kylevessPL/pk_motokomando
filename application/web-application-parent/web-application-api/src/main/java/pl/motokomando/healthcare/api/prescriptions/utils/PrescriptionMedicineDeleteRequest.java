package pl.motokomando.healthcare.api.prescriptions.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionMedicineDeleteRequest {

    @ApiModelProperty(value = "Prescription ID", example = "56")
    @NotNull(message = "Prescription ID is mandatory")
    @Min(value = 1, message = "Prescription ID must be a positive integer value")
    private Integer prescriptionId;
    @ApiModelProperty(value = "Medicine NDC", example = "0536-1261")
    @NotBlank(message = "Product NDC is mandatory")
    private String productNDC;

}
