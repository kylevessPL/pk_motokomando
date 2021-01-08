package pl.motokomando.healthcare.api.mapper;

import org.mapstruct.Mapper;
import pl.motokomando.healthcare.domain.model.utils.PageMeta;
import pl.motokomando.healthcare.dto.utils.PageMetaResponse;

@Mapper
public interface PageMetaMapper {

    PageMetaResponse mapToResponse(PageMeta meta);

}
