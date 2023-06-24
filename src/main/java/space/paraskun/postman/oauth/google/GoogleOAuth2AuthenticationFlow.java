package space.paraskun.postman.oauth.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.oauth2.Oauth2;
import lombok.RequiredArgsConstructor;
import space.paraskun.postman.oauth.OAuthException;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.security.IncorrectAuthenticationMethodException;
import java.io.IOException;

@RequiredArgsConstructor
public class GoogleOAuth2AuthenticationFlow extends AbstractAuthenticationFlow<GoogleOAuth2Credential> {
    private final GoogleAuthorizationCodeFlow codeFlow;

    @Override
    public String getAuthenticationUrl(Object state) {
        return codeFlow.newAuthorizationUrl()
                .setRedirectUri(GoogleOAuthAuthenticationHandler.REDIRECT_URL)
                .setState(state.toString())
                .build();
    }

    @Override
    public void authenticate(String... data) {
        if (data.length == 1) {
            try {
                TokenResponse response = requestToken(data[0]);
                String email = requestEmail(response.getAccessToken());
                // TODO Save user if not exists. If exists and refresh presented, update refresh.
                // TODO Return user.
            } catch (IOException e) {
                session.getAuthenticationConsumer()
                        .onAuthenticationFailure(session.getState(), new OAuthException());
            }
        } else
            session.getAuthenticationConsumer()
                    .onAuthenticationFailure(session.getState(), new IncorrectAuthenticationMethodException());
    }

    private TokenResponse requestToken(String code) throws IOException {
        return codeFlow.newTokenRequest(code)
                .setRedirectUri(GoogleOAuthAuthenticationHandler.REDIRECT_URL)
                .execute();
    }

    private String requestEmail(String accessToken) throws IOException {
        Oauth2 oauth2 = new Oauth2.Builder(
                codeFlow.getTransport(),
                codeFlow.getJsonFactory(),
                new Credential(codeFlow.getMethod()).setAccessToken(accessToken)
        ).setApplicationName("Postman").build();

        return oauth2.userinfo().get().execute().getEmail();
    }
}
