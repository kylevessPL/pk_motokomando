package pl.motokomando.healthcare.domain.model.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SortProperties {

    private final String sortBy;
    private final SortDirection sortDir;

}
