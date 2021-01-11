package pl.motokomando.healthcare.domain.model.bills.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class BillRequestCommand {

    private BigDecimal amount;

}
