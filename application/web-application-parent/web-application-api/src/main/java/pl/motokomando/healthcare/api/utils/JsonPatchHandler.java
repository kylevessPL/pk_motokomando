package pl.motokomando.healthcare.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.validation.Valid;

@Component
@Validated
@RequiredArgsConstructor
public class JsonPatchHandler {

    @Qualifier("jsonPatchObjectMapper")
    private final ObjectMapper mapper;

    @Valid
    public <T> T patch(JsonPatch patch, T targetBean, Class<T> beanClass) {
        JsonStructure target = mapper.convertValue(targetBean, JsonStructure.class);
        JsonValue patched = patch.apply(target);
        return mapper.convertValue(patched, beanClass);
    }

}
