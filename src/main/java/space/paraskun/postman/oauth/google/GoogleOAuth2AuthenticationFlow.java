package space.paraskun.postman.oauth.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.oauth2.Oauth2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import space.paraskun.postman.account.Account;
import space.paraskun.postman.account.AccountService;
import space.paraskun.postman.oauth.IncorrectScopesException;
import space.paraskun.postman.oauth.OAuthException;
import space.paraskun.postman.oauth.RefreshTokenDoesNotPresentException;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.security.IncorrectAuthenticationMethodException;
import java.io.IOException;
import java.util.Optional;

@Component @RequiredArgsConstructor
public class GoogleOAuth2AuthenticationFlow extends AbstractAuthenticationFlow<GoogleOAuth2Credential> {
    private final GoogleAuthorizationCodeFlow codeFlow;
    private final AccountService<GoogleOAuth2Credential> accountService;

    @Override
    public String getAuthenticationUrl() {
        return codeFlow.newAuthorizationUrl()
                .setRedirectUri(GoogleOAuthAuthenticationHandler.REDIRECT_URL)
                .setState(getState())
                .build();
    }

    @Override
    public void authenticate(String... data) {
        if (data.length == 1) {
            try {
                TokenResponse response = requestToken(data[0]);
                String email = requestEmail(response.getAccessToken());
                Account<GoogleOAuth2Credential> account;
                Optional<Account<GoogleOAuth2Credential>> accountOptional =
                        accountService.findByCredentialIdentifier(email);

                if (response.getScope().split(" ").length != 4)
                    throw new IncorrectScopesException();

                if (accountOptional.isPresent()) {
                    account = accountOptional.get();

                    if (response.getRefreshToken() != null) {
                        account.getCredential().setRefreshToken(response.getRefreshToken());
                        account = accountService.save(account);
                    }
                } else {
                    if (response.getRefreshToken() == null)
                        throw new RefreshTokenDoesNotPresentException();

                    account = new Account<>(new GoogleOAuth2Credential(email, response.getRefreshToken()), null);
                    account = accountService.save(account);
                }

                getAuthenticationConsumer()
                        .onAuthenticationSuccess(getState(), account);
            } catch (IOException e) {
                getAuthenticationConsumer()
                        .onAuthenticationFailure(getState(), new OAuthException());
            } catch (OAuthException e) {
                getAuthenticationConsumer()
                        .onAuthenticationFailure(getState(), e);
            }
        } else
            getAuthenticationConsumer()
                    .onAuthenticationFailure(getState(), new IncorrectAuthenticationMethodException());
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
