package pl.motokomando.healthcare.dto.prescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class PrescriptionResponse {

    @ApiModelProperty(value = "Prescription ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "Prescription issue date")
    private LocalDateTime issueDate;
    @ApiModelProperty(value = "Prescription expiration date")
    private LocalDate expirationDate;
    @ApiModelProperty(value = "Prescription additional notes")
    private String notes;

}
