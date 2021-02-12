package pl.motokomando.healthcare.controller.authentication;

import com.google.gson.Gson;
import com.microsoft.alm.oauth2.useragent.AuthorizationException;
import com.microsoft.alm.oauth2.useragent.AuthorizationResponse;
import com.microsoft.alm.oauth2.useragent.UserAgent;
import com.microsoft.alm.oauth2.useragent.UserAgentImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import pl.motokomando.healthcare.model.authentication.AuthenticationModel;
import pl.motokomando.healthcare.model.authentication.utils.Token;
import pl.motokomando.healthcare.model.authentication.utils.UserInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static pl.motokomando.healthcare.controller.utils.WellKnownEndpoints.USER_INFO;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.AUTHENTICATION_FAILURE;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.AUTHENTICATION_STARTED;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.AUTHENTICATION_SUCCESS;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.USER_IDENTIFICATION;

@Slf4j
public class AuthenticationController {

    private final AuthenticationModel authenticationModel;

    private String apiDomain;
    private String oktaDomain;
    private String clientId;
    private String redirectUri;
    private String scope;
    private String grantType;
    private String codeChallengeMethod;

    public AuthenticationController(AuthenticationModel authenticationModel) {
        this.authenticationModel = authenticationModel;
    }

    public void handleLoginButtonClicked() {
        new Thread(() -> {
            try {
                authenticationModel.setAuthenticationStatus(AUTHENTICATION_STARTED);
                loadProperties();
                String codeVerifier = createCodeVerifier();
                String codeChallenge = createCodeChallenge(codeVerifier);
                String code = requestAuthCode(codeChallenge);
                String token = getTokenForCode(code, codeVerifier);
                updateUserToken(token);
                authenticationModel.setAuthenticationStatus(USER_IDENTIFICATION);
                String info = getUserInfo();
                updateUserInfo(info);
                authenticationModel.setAuthenticationStatus(AUTHENTICATION_SUCCESS);
            } catch (IOException | URISyntaxException | NoSuchAlgorithmException | AuthorizationException ex) {
                authenticationModel.setAuthenticationStatus(AUTHENTICATION_FAILURE);
                log.warn("Authentication error: " + ex.getMessage());
            }
        }).start();
    }

    private String requestAuthCode(String codeChallenge) throws MalformedURLException, URISyntaxException, AuthorizationException {
        log.info("OAuth code request");
        URI authorizationEndpoint = getAuthorizationEndpointUri(codeChallenge);
        final URI redirectUri = new URI(this.redirectUri);
        final UserAgent userAgent = new UserAgentImpl();
        final AuthorizationResponse authorizationResponse = userAgent.requestAuthorizationCode(authorizationEndpoint, redirectUri);
        return authorizationResponse.getCode();
    }

    private String getTokenForCode(String code, String codeVerifier) throws URISyntaxException, IOException {
        log.info("Access token request");
        final String tokenUrl = "https://"+ oktaDomain +"/oauth2/default/v1/token";
        final URI redirectUri = new URI(this.redirectUri);
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(tokenUrl);
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", grantType));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("redirect_uri", redirectUri.toString()));
        urlParameters.add(new BasicNameValuePair("client_id", clientId));
        urlParameters.add(new BasicNameValuePair("scope", scope));
        urlParameters.add(new BasicNameValuePair("code_verifier", codeVerifier));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    private URI getAuthorizationEndpointUri(String codeChallenge) throws URISyntaxException, MalformedURLException {
        URIBuilder builder = new URIBuilder();
        URL url = builder
                .setScheme("https")
                .setHost(oktaDomain)
                .setPath("/oauth2/default/v1/authorize")
                .addParameter("client_id", clientId)
                .addParameter("redirect_uri", redirectUri)
                .addParameter("response_type", "code")
                .addParameter("state", "this is a state")
                .addParameter("scope", scope)
                .addParameter("code_challenge_method", codeChallengeMethod)
                .addParameter("code_challenge", codeChallenge)
                .build().toURL();
        return url.toURI();
    }

    private URI getUserInfoEndpointUri() throws URISyntaxException, MalformedURLException {
        URIBuilder builder = new URIBuilder();
        URL url = builder
                .setScheme("http")
                .setHost(apiDomain)
                .setPath(USER_INFO)
                .build().toURL();
        return url.toURI();
    }

    private String getUserInfo() throws IOException, URISyntaxException {
        log.info("User info request");
        URI userInfoEndpoint = getUserInfoEndpointUri();
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(userInfoEndpoint);
        get.setHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType());
        get.setHeader(AUTHORIZATION, "Bearer " + authenticationModel.getToken().getAccessToken());
        HttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());
    }

    private void updateUserToken(String token) {
        Token userToken = new Gson().fromJson(token, Token.class);
        authenticationModel.setToken(userToken);
    }

    private void updateUserInfo(String info) {
        UserInfo userInfo = new Gson().fromJson(info, UserInfo.class);
        authenticationModel.setUserInfo(userInfo);
    }

    private String createCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }

    private String createCodeChallenge(String verifier) throws NoSuchAlgorithmException {
        byte[] bytes = verifier.getBytes(US_ASCII);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes, 0, bytes.length);
        byte[] digest = md.digest();
        return Base64.encodeBase64URLSafeString(digest);
    }

    private void loadProperties() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("okta.properties");
        Properties appProps = new Properties();
        appProps.load(inputStream);
        apiDomain = appProps.getProperty("apiDomain");
        oktaDomain = appProps.getProperty("oktaDomain");
        clientId = appProps.getProperty("oktaClientId");
        redirectUri = appProps.getProperty("redirectUri");
        scope = appProps.getProperty("scope");
        grantType = appProps.getProperty("grantType");
        codeChallengeMethod = appProps.getProperty("codeChallengeMethod");
    }

}
