package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class BasePagedResponse {

    private final Integer id;
    private final String firstName;
    private final String lastName;

}
