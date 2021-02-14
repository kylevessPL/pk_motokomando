package pl.motokomando.healthcare.controller.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.model.SessionStore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.HttpHost.DEFAULT_SCHEME_NAME;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.CURRENT_PAGE;
import static pl.motokomando.healthcare.controller.utils.ResponseHeaders.TOTAL_PAGES;

@SuperBuilder(toBuilder = true)
public abstract class WebClient {

    protected static final String HOST = "localhost:8080";

    protected final SessionStore sessionStore = SessionStore.getInstance();

    @NonNull
    protected final String path;
    @Singular
    @NonNull
    protected final Map<String, String> headers;
    @Singular
    @NonNull
    protected final Map<String, String> parameters;

    abstract public HttpResponse execute() throws URISyntaxException, IOException;

    public void mapErrorResponseAsException(HttpResponse response) throws Exception {
        String responseBody = EntityUtils.toString(response.getEntity());
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        throw new Exception(jsonObject.get("message").getAsString());
    }

    public Map<String, String> extractPageHeaders(HttpResponse response) {
        List<String> acceptedKeys = Arrays.asList(CURRENT_PAGE, TOTAL_PAGES);
        Header[] headers = response.getAllHeaders();
        return Arrays.stream(headers)
                .filter(e -> acceptedKeys.contains(e.getName()))
                .collect(Collectors.toMap(
                        NameValuePair::getName,
                        NameValuePair::getValue,
                        (a, b) -> b));
    }

    protected URI createEndpointURI() throws URISyntaxException, MalformedURLException {
        List<NameValuePair> parameterList = new ArrayList<>();
        parameters.forEach((key, value) -> parameterList.add(new BasicNameValuePair(key, value)));
        URIBuilder builder = new URIBuilder();
        URL url = builder
                .setScheme(DEFAULT_SCHEME_NAME)
                .setHost(HOST)
                .setPath(path)
                .addParameters(parameterList)
                .build().toURL();
        return url.toURI();
    }

    protected Header[] createHeadersArray() {
        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader(
                CONTENT_TYPE,
                APPLICATION_JSON.getMimeType()));
        headerList.add(new BasicHeader(
                AUTHORIZATION,
                "Bearer " + Objects.requireNonNull(sessionStore.getToken()).getAccessToken()));
        parameters.forEach((key, value) -> headerList.add(new BasicHeader(key, value)));
        return headerList.toArray(new Header[0]);
    }

}
