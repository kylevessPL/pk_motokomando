package pl.motokomando.healthcare.api.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class PageResponse<T> {

    private final Integer page;
    private final Integer pageSize;
    private final Integer totalPage;
    private final List<T> content;

}
