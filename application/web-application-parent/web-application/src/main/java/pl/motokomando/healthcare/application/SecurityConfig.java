package pl.motokomando.healthcare.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import pl.motokomando.healthcare.application.utils.CustomAuthorizationRequestResolver;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientRegistrationRepository clientRegistrationRepository;

    @Value(value = "${okta.oauth2.pkce-always}")
    private boolean usePkce;
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/login**",
                        "/api/v1/logout**",
                        "/api/v1/docs/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login()
                .and().oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer
                        .jwt(jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri)))
        );

        if (usePkce) {
            http.oauth2Login()
                    .authorizationEndpoint()
                    .authorizationRequestResolver(new CustomAuthorizationRequestResolver(
                            clientRegistrationRepository, DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
                    ));
        }
    }

}
