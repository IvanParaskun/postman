package space.paraskun.postman.security.consumer;

import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.model.Credential;
import space.paraskun.postman.security.model.CredentialHolder;

public interface AuthenticationConsumer {
	void onAuthenticationSuccess(String state, CredentialHolder<? extends Credential> credentialHolder);
	void onAuthenticationFailure(String state, AuthenticationException exception);
	String getRedirectUrl();
}
