package space.paraskun.postman.security.oauth2.google;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.http.HttpTransport;
import lombok.Setter;
import space.paraskun.postman.security.config.SecurityConfiguration;
import space.paraskun.postman.security.model.Credential;
import java.io.IOException;

@Setter
public class GoogleCredential extends Credential {
    private String refreshToken;

    public GoogleCredential(String identifier, String refreshToken) {
        super(identifier);

        if (refreshToken == null)
            throw new NullPointerException();

        this.refreshToken = refreshToken;
    }

    public String getAccessToken() throws RefreshTokenExpiredException {
        GoogleClientSecrets clientSecrets = SecurityConfiguration.getBean(GoogleClientSecrets.class);
        HttpTransport transport = SecurityConfiguration.getBean(HttpTransport.class);

        try {
            TokenResponse response = new GoogleRefreshTokenRequest(
                    transport,
                    clientSecrets.getDetails().getFactory(),
                    refreshToken,
                    clientSecrets.getDetails().getClientId(),
                    clientSecrets.getDetails().getClientSecret()
            ).execute();

            return response.getAccessToken();
        } catch (IOException e) {
            throw new RefreshTokenExpiredException();
        }
    }
}
