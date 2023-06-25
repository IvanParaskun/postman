package space.paraskun.postman.security;

public class AuthenticationException extends Throwable {
	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message) {
		super(message);
	}
}
