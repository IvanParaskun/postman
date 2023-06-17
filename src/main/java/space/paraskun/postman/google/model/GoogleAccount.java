package space.paraskun.postman.google.model;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;
import space.paraskun.postman.account.AbstractAccount;
import space.paraskun.postman.template.AbstractTemplate;
import java.util.Date;
import java.util.Map;

@Getter
public class GoogleAccount extends AbstractAccount {
	private final String email;
	private final String spreadsheetId;
	private final Credential credential;

	public GoogleAccount(
			String email,
			String spreadsheetId,
			Credential credential,
			Map<String, AbstractTemplate> templates
	) {
		super(templates);

		if (email == null || spreadsheetId == null || credential == null)
			throw new NullPointerException();

		this.email = email;
		this.spreadsheetId = spreadsheetId;
		this.credential = credential;
	}

	@Getter
	public static class Credential {
		private String accessToken;
		private String refreshToken;
		private Date expiresAt;

		@PersistenceCreator
		private Credential(String accessToken, String refreshToken, Date expiresAt) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.expiresAt = expiresAt;
		}

		/**
		 * @param expiresIn Time in seconds before access token becomes invalid.
		 */
		public Credential(String accessToken, String refreshToken, long expiresIn) {
			assert accessToken != null && refreshToken != null;
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.expiresAt = new Date(System.currentTimeMillis() + expiresIn * 1000);
		}

		/**
		 *
		 * @return Whether access token valid or not.
		 */
		public boolean isAccessValid() {
			return expiresAt.after(new Date(System.currentTimeMillis()));
		}

		/**
		 * Refresh access token and expiration date. Used when access token expires.
		 * @param accessToken New access token, received using refresh token.
		 * @param expiresIn New delay in seconds, received using refresh token.
		 */
		public void refresh(String accessToken, long expiresIn) {
			assert accessToken != null;
			this.accessToken = accessToken;
			this.expiresAt = new Date(System.currentTimeMillis() + expiresIn * 1000);
		}

		/**
		 * Replace all credential fields (e.g. reauthenticate)
		 * @param credential New credentials
		 */
		public void replaceAll(Credential credential) {
			assert credential != null;
			accessToken = credential.getAccessToken();
			refreshToken = credential.getRefreshToken();
			expiresAt = credential.getExpiresAt();
		}

		public GoogleCredential googleCredential() {
			return new GoogleCredential().setAccessToken(accessToken);
		}
	}
}
