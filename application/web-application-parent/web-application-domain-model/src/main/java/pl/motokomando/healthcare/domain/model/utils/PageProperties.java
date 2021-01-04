package pl.motokomando.healthcare.domain.model.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class PageProperties {

    private final Integer page;
    private final Integer size;

}
