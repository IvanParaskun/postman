package space.paraskun.postman.security.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.drive.Drive;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.sheets.v4.Sheets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import space.paraskun.postman.security.oauth2.google.GoogleCredential;
import space.paraskun.postman.security.oauth2.google.RefreshTokenExpiredException;

@Configuration
@RequiredArgsConstructor
public class GoogleServices {
    private final GoogleAuthorizationCodeFlow codeFlow;

    public Gmail gmail(GoogleCredential credential) throws RefreshTokenExpiredException {
        return new Gmail.Builder(
                codeFlow.getTransport(),
                codeFlow.getJsonFactory(),
                new Credential(codeFlow.getMethod()).setAccessToken(credential.getAccessToken())
        ).setApplicationName("Postman").build();
    }

    public Sheets sheets(GoogleCredential credential) throws RefreshTokenExpiredException {
        return new Sheets.Builder(
                codeFlow.getTransport(),
                codeFlow.getJsonFactory(),
                new Credential(codeFlow.getMethod()).setAccessToken(credential.getAccessToken())
        ).setApplicationName("Postman").build();
    }

    public Drive drive(GoogleCredential credential) throws RefreshTokenExpiredException {
        return new Drive.Builder(
                codeFlow.getTransport(),
                codeFlow.getJsonFactory(),
                new Credential(codeFlow.getMethod()).setAccessToken(credential.getAccessToken())
        ).setApplicationName("Postman").build();
    }
}
