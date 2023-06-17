package space.paraskun.postman.google.auth;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.oauth2.Oauth2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import space.paraskun.postman.google.model.GoogleAccount;
import space.paraskun.postman.google.datasource.GoogleAccountService;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class DefaultGoogleAuthService implements AuthenticationService {
    private final GoogleAuthorizationCodeFlow authorizationCodeFlow;
    private GoogleAccountService accountService;
    private final NetHttpTransport netHttpTransport;
    private final JsonFactory jsonFactory;
    private final GoogleClientSecrets clientSecrets;
    @Value("${oauth2.client.redirect-uri}") private String redirectUrl;

    @Override
    public void setAccountService(GoogleAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String getAuthUrl(String state) {
        return authorizationCodeFlow.newAuthorizationUrl()
                .setState(state)
                .setRedirectUri(redirectUrl).build();
    }

    @Override
    public boolean refreshCredential(GoogleAccount account) {
        try {
            TokenResponse tokenResponse = new GoogleRefreshTokenRequest(
                    netHttpTransport, jsonFactory,
                    account.getCredential().getRefreshToken(),
                    clientSecrets.getDetails().getClientId(),
                    clientSecrets.getDetails().getClientSecret()
            ).execute();

            account.getCredential().refresh(tokenResponse.getAccessToken(), tokenResponse.getExpiresInSeconds());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void authorize(String code, String state) {
        try {
            GoogleAccount.Credential credential = getCredential(code);
            String email = getEmail(credential);
            String spreadsheetId = createSpreadsheet(credential);
            GoogleAccount googleAccount = new GoogleAccount(email, spreadsheetId, credential, null);
            accountService.save(googleAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GoogleAccount.Credential getCredential(String code) throws IOException {
        TokenResponse tokenResponse = authorizationCodeFlow.newTokenRequest(code)
                .setRedirectUri(redirectUrl)
                .execute();
        return new GoogleAccount.Credential(
                tokenResponse.getAccessToken(),
                tokenResponse.getRefreshToken(),
                tokenResponse.getExpiresInSeconds()
        );
    }

    private String getEmail(GoogleAccount.Credential credential) throws IOException {
        Oauth2 oauth2 = new Oauth2.Builder(
                netHttpTransport,
                jsonFactory,
                credential.googleCredential()
        ).setApplicationName("Postman").build();

        return oauth2.userinfo().get().execute().getEmail();
    }
}
