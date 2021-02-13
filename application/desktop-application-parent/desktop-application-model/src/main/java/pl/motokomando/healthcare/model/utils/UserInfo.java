package pl.motokomando.healthcare.model.utils;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class UserInfo {

    @SerializedName(value = "preferredUsername")
    private final String username;
    private final String email;
    @SerializedName(value = "givenName")
    private final String firstName;
    @SerializedName(value = "familyName")
    private final String lastName;

}
