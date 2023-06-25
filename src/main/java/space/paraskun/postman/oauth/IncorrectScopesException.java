package space.paraskun.postman.oauth;

public class IncorrectScopesException extends OAuthException {
    public IncorrectScopesException(String message) {
        super(message);
    }

    public IncorrectScopesException() {
        super();
    }
}
