package space.paraskun.postman.security;

import space.paraskun.postman.account.AbstractAccount;

public interface AuthenticationConsumer {
	void onAuthenticationSuccess(AbstractAccount account, Object... params);
	void onAuthenticationFailure(AuthenticationException exception);
}
