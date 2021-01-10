package pl.motokomando.healthcare.dto.bills;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class BillResponse {

    @ApiModelProperty(value = "Bill ID", example = "1")
    private Integer id;
    @ApiModelProperty(value = "Bill issue date")
    private LocalDateTime issueDate;
    @ApiModelProperty(value = "Amount", example = "150.00")
    private BigDecimal amount;

}
