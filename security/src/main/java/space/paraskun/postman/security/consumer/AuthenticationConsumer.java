package space.paraskun.postman.security.consumer;

import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.model.Credential;

public interface AuthenticationConsumer {
	void onAuthenticationSuccess(String state, Credential credential);
	void onAuthenticationFailure(String state, AuthenticationException exception);
	String getRedirectUrl();
}
