package pl.motokomando.healthcare.api.bills.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class BillRequest {

    @Schema(description = "Amount", example = "150.00")
    @NotNull(message = "Bill amount value is mandatory")
    @DecimalMin(value = "0.0", message = "Amount must be a positive decimal number")
    @Digits(integer = 10, fraction = 2, message = "Not a valid price value")
    private BigDecimal amount;

}
