package space.paraskun.postman.oauth.google;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import lombok.Getter;
import lombok.ToString;
import org.apache.http.client.HttpResponseException;
import org.springframework.data.annotation.PersistenceCreator;
import space.paraskun.postman.ContextHolder;
import space.paraskun.postman.oauth.OAuthCredential;
import space.paraskun.postman.oauth.OAuthException;
import space.paraskun.postman.oauth.RefreshTokenExpiredException;
import java.io.IOException;

@Getter @ToString(callSuper = true)
public class GoogleOAuth2Credential extends OAuthCredential {
    private String refreshToken;

    @PersistenceCreator
    public GoogleOAuth2Credential(String identifier, String refreshToken) {
        super(identifier);

        if (refreshToken == null)
            throw new NullPointerException();

        this.refreshToken = refreshToken;
    }

    @Override
    public String getAccessToken() throws OAuthException {
        GoogleAuthorizationCodeFlow codeFlow = ContextHolder.getBean(GoogleAuthorizationCodeFlow.class);
        GoogleClientSecrets clientSecrets = ContextHolder.getBean(GoogleClientSecrets.class);
        GoogleRefreshTokenRequest request = new GoogleRefreshTokenRequest(
                codeFlow.getTransport(),
                codeFlow.getJsonFactory(),
                refreshToken,
                clientSecrets.getDetails().getClientId(),
                clientSecrets.getDetails().getClientSecret()
        );

        try {
            TokenResponse response = request.execute();
            return response.getAccessToken();
        } catch (IOException e) {
            if (e instanceof HttpResponseException httpExc && httpExc.getStatusCode() == 400)
                throw new RefreshTokenExpiredException();

            throw new OAuthException();
        }
    }

    public void setRefreshToken(String refreshToken) {
        if (refreshToken == null)
            throw new NullPointerException();

        this.refreshToken = refreshToken;
    }
}
