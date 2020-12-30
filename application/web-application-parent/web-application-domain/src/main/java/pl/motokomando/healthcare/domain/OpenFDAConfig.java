package pl.motokomando.healthcare.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class OpenFDAConfig {

    private static final String BASE_URL = "https://api.fda.gov/drug/ndc.json";

    @Bean
    public WebClient openFDAClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
                .build();
    }

}
