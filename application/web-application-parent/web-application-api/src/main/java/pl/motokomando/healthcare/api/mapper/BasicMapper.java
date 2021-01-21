package pl.motokomando.healthcare.api.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.utils.Basic;
import pl.motokomando.healthcare.dto.utils.BasicResponse;

@Mapper
public interface BasicMapper {

    BasicResponse mapToBasicResponse(Basic basic);

}
