package space.paraskun.postman.oauth;

public class RefreshTokenExpiredException extends OAuthException {
	public RefreshTokenExpiredException(String message) {
		super(message);
	}
}
