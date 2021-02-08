package pl.motokomando.healthcare.model.authentication.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Token {

    private final String accessToken;
    private final String idToken;

}
