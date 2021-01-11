package pl.motokomando.healthcare.domain.model.bills.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BillPatchRequestCommand {

    private Integer id;
    private LocalDateTime issueDate;
    private BigDecimal amount;

}
