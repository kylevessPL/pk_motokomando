package pl.motokomando.healthcare.api.authorization;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import pl.motokomando.healthcare.api.utils.UserInfoResponseExample;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Authorization API", description = "API performing user authorization operations")
@RequiredArgsConstructor
public class AuthorizationServiceController {

    @Value("${spring.security.oauth2.client.provider.okta.user-info-uri}")
    private String userInfoUri;

    @Hidden
    @GetMapping("/")
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    @Operation(
            summary = "User info",
            description = "Retrieve user information",
            operationId = "userInfo"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched user details",
                    content = @Content(schema = @Schema(implementation = UserInfoResponseExample.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/api/v1/authorization/userinfo", produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> userInfo(HttpServletRequest request, @AuthenticationPrincipal OidcUser principal) {
        Map<String, Object> userAttributes;
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null) {
            return convertMapToCamelCase(principal.getUserInfo().getClaims());
        }
        String tokenValue = authHeader.replace("Bearer", "").trim();
        userAttributes = WebClient.builder()
                .filter(oauth2Credentials(tokenValue)).build()
                .get().uri(userInfoUri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                .block();
        return convertMapToCamelCase(userAttributes);
    }

    private ExchangeFilterFunction oauth2Credentials(String tokenValue) {
        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                            .header(AUTHORIZATION,
                                    "Bearer " + tokenValue)
                            .build();
                    return Mono.just(authorizedRequest);
                });
    }

    private Map<String, Object> convertMapToCamelCase(Map<String, Object> userAttributes) {
        userAttributes = userAttributes.entrySet().stream()
                .collect(Collectors.toMap(entry ->
                                CaseUtils.toCamelCase(entry.getKey(), false, '_'),
                                Map.Entry::getValue,
                                (x, y) -> y,
                                LinkedHashMap::new));
        return userAttributes;
    }

}
