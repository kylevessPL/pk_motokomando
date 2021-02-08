package pl.motokomando.healthcare.model.authentication.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class UserInfo {

    private final String userName;
    private final String email;
    private final String firstName;
    private final String lastName;

}
