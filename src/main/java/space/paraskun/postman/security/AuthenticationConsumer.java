package space.paraskun.postman.security;

import space.paraskun.postman.account.Account;

public interface AuthenticationConsumer {
	void onAuthenticationSuccess(Object state, Account<? extends Credential> account);
	void onAuthenticationFailure(Object state, AuthenticationException exception);
}
