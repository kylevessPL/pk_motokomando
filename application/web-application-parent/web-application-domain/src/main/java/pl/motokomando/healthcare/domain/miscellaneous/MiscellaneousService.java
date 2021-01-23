package pl.motokomando.healthcare.domain.miscellaneous;

import pl.motokomando.healthcare.domain.model.miscellaneous.DateAvailable;

import java.time.LocalDateTime;

public interface MiscellaneousService {

    DateAvailable checkDateAvailability(LocalDateTime date);

}
