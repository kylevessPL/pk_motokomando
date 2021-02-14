package pl.motokomando.healthcare.controller.utils;

import lombok.experimental.SuperBuilder;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.nio.charset.StandardCharsets.UTF_8;

@SuperBuilder(toBuilder = true)
public class PostClient extends WebClient {

    private final String body;

    @Override
    public HttpResponse execute() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URI endpointURI = createEndpointURI();
        Header[] headersArray = createHeadersArray();
        HttpPost post = new HttpPost(endpointURI);
        post.setHeaders(headersArray);
        post.setEntity(new StringEntity(body, UTF_8));
        return client.execute(post);
    }

}
