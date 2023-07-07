package space.paraskun.postman.security.oauth2.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.oauth2.Oauth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.model.CredentialHolderRepository;
import space.paraskun.postman.security.oauth2.IncompleteSetOfScopesException;
import space.paraskun.postman.security.session.AuthenticationSessionRepository;
import java.io.IOException;

@Component @Scope("prototype")
public class GoogleOAuth2AuthenticationFlow extends AbstractAuthenticationFlow<GoogleCredential> {
    private final GoogleAuthorizationCodeFlow codeFlow;
    private final CredentialHolderRepository<GoogleCredential> holderRepository;

    @Autowired
    public GoogleOAuth2AuthenticationFlow(
            AuthenticationSessionRepository repository,
            GoogleAuthorizationCodeFlow codeFlow,
            CredentialHolderRepository<GoogleCredential> holderRepository
    ) {
        super(repository);
        this.codeFlow = codeFlow;
        this.holderRepository = holderRepository;
    }

    @Override
    public String getAuthenticationUrl() {
        return codeFlow.newAuthorizationUrl()
                .setRedirectUri(GoogleOAuth2AuthenticationHandler.REDIRECT_URL)
                .setState(session.getState()).build();
    }

    @Override
    protected void authenticate(String... data) {
        assert data.length == 1;

        try {
            TokenResponse tokenResponse = requestToken(data[0]);

            if (tokenResponse.getScope().split(" ").length != 4)
                throw new IncompleteSetOfScopesException();

            String email = requestEmail(tokenResponse.getAccessToken());
            GoogleCredential credential = holderRepository.findCredentialByIdentifier(email);

            if (credential == null) {
                if (tokenResponse.getRefreshToken() == null)
                    throw new RefreshTokenNotPresentException();

                credential = new GoogleCredential(email, tokenResponse.getRefreshToken());
            } else
                if (tokenResponse.getRefreshToken() != null)
                    credential.setRefreshToken(tokenResponse.getRefreshToken());

            session.getAuthenticationConsumer()
                    .onAuthenticationSuccess(session.getState(), credential);
        } catch (IOException e) {
            session.getAuthenticationConsumer()
                    .onAuthenticationFailure(session.getState(), new AuthenticationException());
        } catch (AuthenticationException e) {
            session.getAuthenticationConsumer()
                    .onAuthenticationFailure(session.getState(), e);
        }
    }

    private TokenResponse requestToken(String code) throws IOException {
        return codeFlow.newTokenRequest(code)
                .setRedirectUri(GoogleOAuth2AuthenticationHandler.REDIRECT_URL)
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
