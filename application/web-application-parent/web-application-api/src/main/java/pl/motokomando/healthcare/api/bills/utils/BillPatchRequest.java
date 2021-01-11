package pl.motokomando.healthcare.api.bills.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class BillPatchRequest {

    @ApiModelProperty(value = "Bill ID", example = "1")
    @NotNull(message = "Bill ID is mandatory")
    @Min(value = 1, message = "Bill ID must be a positive integer value")
    private Integer id;
    @ApiModelProperty(value = "Bill issue date")
    @NotNull(message = "Bill issue date is mandatory")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime issueDate;
    @ApiModelProperty(value = "Amount", example = "150.00")
    @NotNull(message = "Bill amount value is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be a positive decimal number")
    @Digits(integer = 10, fraction = 2, message = "Not a valid price value")
    private BigDecimal amount;

}
