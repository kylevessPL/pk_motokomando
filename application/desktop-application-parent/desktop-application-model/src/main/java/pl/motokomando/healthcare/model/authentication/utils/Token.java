package pl.motokomando.healthcare.model.authentication.utils;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Token {

    @SerializedName(value = "access_token")
    private final String accessToken;
    @SerializedName(value = "id_token")
    private final String idToken;

}
