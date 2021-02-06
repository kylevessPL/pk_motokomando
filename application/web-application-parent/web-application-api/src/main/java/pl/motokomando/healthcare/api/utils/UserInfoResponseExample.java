package pl.motokomando.healthcare.api.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public class UserInfoResponseExample {

    @Schema(example = "00u4sgw6e1uExfjyB5d6")
    String sub;
    @Schema(example = "John Doe")
    String name;
    @Schema(example = "en-US")
    String locale;
    @Schema(example = "example@example.com")
    String email;
    @Schema(example = "example@example.com")
    String preferredUsername;
    @Schema(example = "John")
    String givenName;
    @Schema(example = "Doe")
    String familyName;
    @Schema(example = "America/Los_Angeles")
    String zoneinfo;
    @Schema(example = "1612133544")
    Long updatedAt;
    @Schema
    boolean emailVerified;

}
