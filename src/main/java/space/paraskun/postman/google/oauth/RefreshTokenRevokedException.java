package space.paraskun.postman.google.oauth;

public class RefreshTokenRevokedException extends OAuthException {
	public RefreshTokenRevokedException(String message) {
		super(message);
	}
}
