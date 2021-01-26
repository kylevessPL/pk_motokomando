package pl.motokomando.healthcare.api.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public class JsonPatchExample {

    @Schema(description = "Patch operation", allowableValues = "add, replace, remove", example = "replace")
    String op;
    @Schema(description = "Resource path", example = "/notes")
    String path;
    @Schema(description = "Resource new value", example = "Simple description example")
    String value;

}
