package space.paraskun.postman.security;

public class IncorrectAuthenticationMethodException extends AuthenticationException {
    public IncorrectAuthenticationMethodException() {
        super();
    }

    public IncorrectAuthenticationMethodException(String message) {
        super(message);
    }
}
