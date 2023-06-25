package space.paraskun.postman.security.oauth2;

import space.paraskun.postman.security.AuthenticationException;

public class IncompleteSetOfScopesException extends AuthenticationException {
    public IncompleteSetOfScopesException() {
        super();
    }

    public IncompleteSetOfScopesException(String message) {
        super(message);
    }
}
