package pl.motokomando.healthcare.controller.utils;

import lombok.experimental.SuperBuilder;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SuperBuilder(toBuilder = true)
public class DeleteClient extends WebClient {

    @Override
    public HttpResponse execute() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URI endpointURI = createEndpointURI();
        Header[] headersArray = createHeadersArray();
        HttpDelete delete = new HttpDelete(endpointURI);
        delete.setHeaders(headersArray);
        return client.execute(delete);
    }

}
