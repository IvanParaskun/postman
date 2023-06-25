package space.paraskun.postman.oauth;

import space.paraskun.postman.security.Credential;

public abstract class OAuthCredential extends Credential {
	public abstract String getAccessToken() throws OAuthException;

	public OAuthCredential(String identifier) {
		super(identifier);
	}
}
