package pl.motokomando.healthcare.controller.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

@SuperBuilder(toBuilder = true)
public class PatchClient extends WebClient {

    private static final String PATCH_BODY_OPERATION = "op";
    private static final String PATCH_BODY_PATH = "path";
    private static final String PATCH_BODY_VALUE = "value";

    private static final String APPLICATION_JSON_PATCH = "application/json-patch+json";

    @Singular
    private final Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operations;

    @Override
    public HttpResponse execute() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URI endpointURI = createEndpointURI();
        Header[] headersArray = createHeadersArray();
        String body = createJsonPatchBody();
        HttpPatch patch = new HttpPatch(endpointURI);
        patch.addHeader(AUTHORIZATION, "Bearer " + Objects.requireNonNull(sessionStore.getToken()).getAccessToken());
        patch.addHeader(CONTENT_TYPE, APPLICATION_JSON_PATCH);
        patch.setEntity(new StringEntity(body, UTF_8));
        return client.execute(patch);
    }

    private String createJsonPatchBody() {
        JsonArray jsonArray = new JsonArray();
        operations.forEach((key, value) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(PATCH_BODY_OPERATION, key.getName());
            jsonObject.addProperty(PATCH_BODY_PATH, value.getKey());
            value.getValue().ifPresent(e -> jsonObject.addProperty(PATCH_BODY_VALUE, e));
            jsonArray.add(jsonObject);
        });
        return new Gson().toJson(jsonArray);
    }

}
