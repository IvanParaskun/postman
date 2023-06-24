package space.paraskun.postman.security;

import space.paraskun.postman.security.AuthenticationException;

public class SessionExpiredException extends AuthenticationException {
	public SessionExpiredException() {
		super();
	}

	public SessionExpiredException(String message) {
		super(message);
	}
}
