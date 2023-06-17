package space.paraskun.postman.google.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class OAuth2Config {
    private final ApplicationContext applicationContext;
    public static final List<String> SCOPES = List.of(
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/gmail.send",
            "https://www.googleapis.com/auth/spreadsheets"
    );

    @Bean
    public NetHttpTransport httpTransport() {
        return new NetHttpTransport();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return GsonFactory.getDefaultInstance();
    }

    @Bean
    public GoogleAuthorizationCodeFlow codeFlow() {
        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport(),
                jsonFactory(),
                clientSecrets().getDetails().getClientId(),
                clientSecrets().getDetails().getClientSecret(),
                SCOPES
        ).setAccessType("offline").build();
    }

    @Bean
    public GoogleClientSecrets clientSecrets() {
        try (InputStream inputStream = applicationContext
                .getResource("classpath:client-secret.json").getInputStream()
        ) {
            return GoogleClientSecrets.load(jsonFactory(), new InputStreamReader(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
