package space.paraskun.postman.oauth;

import space.paraskun.postman.security.Credential;

public abstract class OAuthCredential extends Credential {
	abstract public String getAccessToken() throws OAuthException;
}
