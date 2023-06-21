package space.paraskun.postman.oauth;

import space.paraskun.postman.security.AuthenticationException;

public class OAuthException extends AuthenticationException {
	public OAuthException(String message) {
		super(message);
	}
}
