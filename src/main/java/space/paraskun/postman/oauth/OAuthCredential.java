package space.paraskun.postman.oauth;

import space.paraskun.postman.security.Credential;

public interface OAuthCredential extends Credential {
	String getAccessToken() throws OAuthException;
}
