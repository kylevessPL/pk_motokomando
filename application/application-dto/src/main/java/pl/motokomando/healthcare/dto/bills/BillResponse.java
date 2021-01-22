package pl.motokomando.healthcare.dto.bills;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class BillResponse {

    @Schema(description = "Bill ID", example = "1")
    private Integer id;
    @Schema(description = "Bill issue date")
    private LocalDateTime issueDate;
    @Schema(description = "Amount", example = "150.00")
    private BigDecimal amount;

}
