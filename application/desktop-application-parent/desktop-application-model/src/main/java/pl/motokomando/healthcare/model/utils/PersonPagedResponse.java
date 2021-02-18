package pl.motokomando.healthcare.model.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class PersonPagedResponse {

    private final Integer id;
    private final String firstName;
    private final String lastName;

}
