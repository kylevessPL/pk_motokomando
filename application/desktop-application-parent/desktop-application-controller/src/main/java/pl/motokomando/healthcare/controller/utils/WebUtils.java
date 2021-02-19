package pl.motokomando.healthcare.controller.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static pl.motokomando.healthcare.controller.utils.JsonPatchOperation.ADD;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;

public class WebUtils {

    public static void mapErrorResponseAsException(HttpResponse response) throws Exception {
        String responseBody = EntityUtils.toString(response.getEntity());
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        throw new Exception(jsonObject.get("message").getAsString());
    }

    public static Map<String, String> extractPageHeaders(HttpResponse response) {
        List<String> acceptedKeys = Arrays.asList(CURRENT_PAGE, TOTAL_PAGES);
        Header[] headers = response.getAllHeaders();
        return Arrays.stream(headers)
                .filter(e -> acceptedKeys.contains(e.getName()))
                .collect(Collectors.toMap(
                        NameValuePair::getName,
                        NameValuePair::getValue,
                        (a, b) -> b));
    }

    public static Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> createAddOperationsMap(Map<String, String> objectsDiff) {
        Map<JsonPatchOperation, Map.Entry<String, Optional<String>>> operationsMap = new HashMap<>();
        objectsDiff.forEach((key, value) -> operationsMap.put(
                ADD,
                new AbstractMap.SimpleEntry<>("/" + key, Optional.ofNullable(value))));
        return operationsMap;
    }

    public static void sendUpdatePersonRequest(String path, String body) throws Exception {
        WebClient client = PutClient.builder()
                .path(path)
                .body(body)
                .build();
        HttpResponse response = client.execute();
        if (response.getStatusLine().getStatusCode() != SC_NO_CONTENT) {
            WebUtils.mapErrorResponseAsException(response);
        }
    }

    public static HttpResponse sendGetPageRequest(
            String path,
            Map<String, String> pathVariables,
            int page, int size) throws Exception {
        WebClient client = GetClient.builder()
                .path(path)
                .pathVariables(pathVariables)
                .parameter("page", String.valueOf(page))
                .parameter("size", String.valueOf(size))
                .build();
        HttpResponse response = client.execute();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != SC_NO_CONTENT && statusCode != SC_OK) {
            WebUtils.mapErrorResponseAsException(response);
        }
        return response;
    }

}
