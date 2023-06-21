package space.paraskun.postman.security.flow;

import space.paraskun.postman.security.AuthenticationException;

public class FlowDoesNotExistsException extends AuthenticationException {
	public FlowDoesNotExistsException(String message) {
		super(message);
	}
}
