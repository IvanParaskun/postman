package space.paraskun.postman.oauth.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GoogleOAuthConfiguration {
	private final ApplicationContext context;
	private static final String SCOPE_BASE = "https://www.googleapis.com/auth/";
	public static final List<String> SCOPES = List.of(
			SCOPE_BASE + "userinfo.email",
			SCOPE_BASE + "gmail.send",
			SCOPE_BASE + "drive"
	);

	@Bean
	public HttpTransport httpTransport() {
		return new NetHttpTransport();
	}

	@Bean
	public JsonFactory jsonFactory() {
		return GsonFactory.getDefaultInstance();
	}

	@Bean
	public GoogleClientSecrets clientSecrets() {
		try (InputStream inputStream = context.getResource("classpath:client-secret.json").getInputStream()) {
			Reader reader = new InputStreamReader(inputStream);
			return GoogleClientSecrets.load(jsonFactory(), reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public GoogleAuthorizationCodeFlow codeFlow() {
		return new GoogleAuthorizationCodeFlow.Builder(
				httpTransport(),
				jsonFactory(),
				clientSecrets(),
				SCOPES
		).setAccessType("offline").build();
	}
}
