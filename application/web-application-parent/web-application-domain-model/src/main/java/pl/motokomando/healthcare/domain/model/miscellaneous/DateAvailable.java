package pl.motokomando.healthcare.domain.model.miscellaneous;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class DateAvailable {

    private final LocalDateTime date;
    private final boolean available;

}
