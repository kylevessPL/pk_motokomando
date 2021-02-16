package pl.motokomando.healthcare.controller.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;

public class WebResponseUtils {

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

}
