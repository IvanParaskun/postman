package space.paraskun.postman.security;

import org.springframework.stereotype.Service;
import space.paraskun.postman.account.AbstractAccount;

@Service("consoleAuthenticationConsumer")
public class ConsoleAuthenticationConsumer implements AuthenticationConsumer {
	@Override
	public void onAuthenticationSuccess(AbstractAccount account, Object... params) {
		System.out.println("Authentication success for account: " + account);
	}

	@Override
	public void onAuthenticationFailure(AuthenticationException exception) {
		System.out.println("Authentication failed: " + exception.getMessage());
	}
}
