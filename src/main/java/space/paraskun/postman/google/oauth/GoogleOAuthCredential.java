package space.paraskun.postman.google.oauth;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;
import space.paraskun.postman.security.Credential;
import space.paraskun.postman.security.OAuthCredential;

@Getter
public class GoogleOAuthCredential implements OAuthCredential {
	private String email;
	private String refreshToken;

	@PersistenceCreator
	public GoogleOAuthCredential(String email, String refreshToken) {
		if (email == null || refreshToken == null)
			throw new NullPointerException();

		this.email = email;
		this.refreshToken = refreshToken;
	}

	@Override
	public void reload(Credential credential) {
		if (credential instanceof GoogleOAuthCredential googleCredential) {
			this.email = googleCredential.getEmail();
			this.refreshToken = googleCredential.getRefreshToken();
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public String getAccessToken() {
		return null;
	}
}
