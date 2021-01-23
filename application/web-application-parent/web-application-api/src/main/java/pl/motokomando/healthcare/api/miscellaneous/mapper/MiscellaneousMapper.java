package pl.motokomando.healthcare.api.miscellaneous.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.miscellaneous.DateAvailable;
import pl.motokomando.healthcare.dto.miscellaneous.DateAvailableResponse;

@Mapper
public interface MiscellaneousMapper {

    DateAvailableResponse mapToResponse(DateAvailable dateAvailable);

}
