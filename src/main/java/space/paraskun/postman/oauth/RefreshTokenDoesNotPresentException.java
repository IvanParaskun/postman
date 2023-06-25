package space.paraskun.postman.oauth;

public class RefreshTokenDoesNotPresentException extends OAuthException {
    public RefreshTokenDoesNotPresentException(String message) {
        super(message);
    }

    public RefreshTokenDoesNotPresentException() {
        super();
    }
}
