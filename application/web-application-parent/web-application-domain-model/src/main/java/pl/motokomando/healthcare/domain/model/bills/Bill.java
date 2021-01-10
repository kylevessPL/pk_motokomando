package pl.motokomando.healthcare.domain.model.bills;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class Bill {

    private final Integer id;
    private final LocalDateTime issueDate;
    private final BigDecimal amount;

}
