package space.paraskun.postman.security.oauth2.google;

import space.paraskun.postman.security.AuthenticationException;

public class RefreshTokenNotPresentException extends AuthenticationException {
    public RefreshTokenNotPresentException() {
        super();
    }

    public RefreshTokenNotPresentException(String message) {
        super(message);
    }
}
