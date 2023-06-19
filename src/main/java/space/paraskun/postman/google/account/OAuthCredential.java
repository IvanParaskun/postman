package space.paraskun.postman.google.account;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import lombok.AllArgsConstructor;
import lombok.Getter;
import space.paraskun.postman.security.Credential;
import java.util.Date;

@AllArgsConstructor @Getter
public class OAuthCredential implements Credential {
	private String accessToken;
	private final String refreshToken;
	private Date expiresAt;

	/**
	 * @param expiresIn Expiration delay in seconds.
	 */
	public OAuthCredential(
			String accessToken,
			String refreshToken,
			long expiresIn
	) {
		if (accessToken == null || refreshToken == null)
			throw new NullPointerException();

		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresAt = new Date(System.currentTimeMillis() + expiresIn * 1000);
	}

	public boolean isValid() {
		return expiresAt.after(new Date(System.currentTimeMillis()));
	}

	/**
	 * @param expiresIn Expiration delay in seconds.
	 */
	public void refresh(String accessToken, long expiresIn) {
		if (accessToken == null)
			throw new NullPointerException();

		this.accessToken = accessToken;
		this.expiresAt = new Date(System.currentTimeMillis() + expiresIn * 1000);
	}

	public void replace(OAuthCredential credential) {
		if (credential == null)
			throw new NullPointerException();
	}

	public GoogleCredential toGoogleCredential() {
		return new GoogleCredential()
				.setAccessToken(accessToken)
				.setRefreshToken(refreshToken)
				.setExpirationTimeMilliseconds(expiresAt.getTime());
	}
}
