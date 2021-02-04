package pl.motokomando.healthcare.application;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.OAUTH2;

@Configuration
public class OpenAPIConfig {

    @Value(value = "${okta.oauth2.authorization-url}")
    private String authorizationUrl;
    @Value(value = "${okta.oauth2.token-url}")
    private String tokenUrl;

    @Bean
    public OpenAPI openAPI() {
        final String apiVersion = "1.0";
        final String apiTitle = "Healthcare Management OpenAPI";
        final String apiDescription = "OpenAPI Documentation for Healthcare Management";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(OAUTH2.name()))
                .components(new Components()
                                .addSecuritySchemes(OAUTH2.name(),
                                        new SecurityScheme()
                                                .name(OAUTH2.name())
                                                .type(OAUTH2)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .in(HEADER)
                                                .flows(new OAuthFlows()
                                                        .authorizationCode(new OAuthFlow()
                                                                .authorizationUrl(authorizationUrl)
                                                                .tokenUrl(tokenUrl)))))
                .info(new Info()
                        .title(apiTitle)
                        .description(apiDescription)
                        .version(apiVersion));
    }

}
