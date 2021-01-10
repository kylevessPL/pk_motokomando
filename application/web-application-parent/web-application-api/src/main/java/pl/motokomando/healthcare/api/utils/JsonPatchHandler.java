package pl.motokomando.healthcare.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;

@Component
@RequiredArgsConstructor
public class JsonPatchHandler {

    @Qualifier("jsonPatchObjectMapper")
    private final ObjectMapper mapper;

    public <T> T patch(JsonPatch patch, T targetBean, Class<T> beanClass) {
        JsonStructure target = mapper.convertValue(targetBean, JsonStructure.class);
        JsonValue patched = patch.apply(target);
        return mapper.convertValue(patched, beanClass);
    }

}
