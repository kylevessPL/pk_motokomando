package pl.motokomando.healthcare.api.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.motokomando.healthcare.dto.utils.PageMetaResponse;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class PageResponse<T> {

    private final int pageSize;
    private final PageMetaResponse pageMeta;
    private final List<T> content;

}
