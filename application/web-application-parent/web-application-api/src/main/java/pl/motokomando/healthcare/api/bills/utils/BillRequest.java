package pl.motokomando.healthcare.api.bills.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class BillRequest {

    @ApiModelProperty(value = "Amount", example = "150.00")
    @NotNull(message = "Bill amount value is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be a positive decimal number")
    @Digits(integer = 10, fraction = 2, message = "Not a valid price value")
    private BigDecimal amount;

}
