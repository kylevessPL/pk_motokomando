package pl.motokomando.healthcare.api.authorization;

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
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import pl.motokomando.healthcare.api.utils.UserInfoResponseExample;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Authorization API", description = "API performing user authorization operations")
@RequiredArgsConstructor
public class AuthorizationServiceController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    @Value("${okta.login-url}")
    private String loginUrl;
    @Value("${okta.logout-url}")
    private String logoutUrl;
    @Value("${okta.oauth2.postLogoutRedirectUri}")
    private String logoutRedirectUrl;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/api/v1/login")
    public ModelAndView login() {
        return new ModelAndView("redirect:" + loginUrl);
    }

    @GetMapping("/api/v1/logout")
    public ModelAndView logout(ModelMap model,
            HttpServletRequest request,
            @AuthenticationPrincipal OidcUser principal) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        if (principal == null) {
            return new ModelAndView("redirect:" + logoutRedirectUrl);
        }
        model.addAttribute("id_token_hint", principal.getIdToken().getTokenValue());
        model.addAttribute("post_logout_redirect_uri", logoutRedirectUrl);
        return new ModelAndView("redirect:" + logoutUrl, model);
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
    @GetMapping(value = "/api/v1/userinfo", produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> userInfo(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        Map<String, Object> userAttributes = Collections.emptyMap();
        String userInfoEndpointUri = authorizedClient.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        if (!userInfoEndpointUri.isEmpty()) {
            userAttributes = WebClient.builder()
                    .filter(oauth2Credentials(authorizedClient)).build()
                    .get().uri(userInfoEndpointUri)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LinkedHashMap<String, Object>>() {})
                    .block();
        }
        userAttributes = convertMapToCamelCase(userAttributes);
        return userAttributes;
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

    private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                            .header(AUTHORIZATION,
                                    "Bearer " + authorizedClient.getAccessToken().getTokenValue())
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
