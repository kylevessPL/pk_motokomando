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
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.OAUTH2;

@Configuration
public class OpenAPIConfig {

    @Value(value = "${spring.security.oauth2.client.provider.okta.authentication-uri}")
    private String authorizationUrl;
    @Value(value = "${spring.security.oauth2.client.provider.okta.token-uri}")
    private String tokenUrl;

    @Bean
    public OpenAPI openAPI() {
        final String apiVersion = "1.0";
        final String apiTitle = "Healthcare Management OpenAPI";
        final String apiDescription = "OpenAPI Documentation for Healthcare Management";

        final String oAuth2SchemeName = "OAuth2";
        final String bearerSchemeName = "BearerToken";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(oAuth2SchemeName))
                .addSecurityItem(new SecurityRequirement().addList(bearerSchemeName))
                .components(new Components()
                                .addSecuritySchemes(oAuth2SchemeName, new SecurityScheme()
                                        .name(oAuth2SchemeName)
                                        .description("Autorize using Okta OAuth2 token server")
                                        .type(OAUTH2)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(HEADER)
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl(authorizationUrl)
                                                        .tokenUrl(tokenUrl))))
                                .addSecuritySchemes(bearerSchemeName, new SecurityScheme()
                                        .name(bearerSchemeName)
                                        .description("Autorize using Bearer token")
                                        .type(HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title(apiTitle)
                        .description(apiDescription)
                        .version(apiVersion));
    }

}
