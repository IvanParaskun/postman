package space.paraskun.postman.google.oauth;

import com.google.api.services.oauth2.model.Userinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import space.paraskun.postman.google.account.GoogleLinkedAccount;
import space.paraskun.postman.google.account.OAuthCredential;
import space.paraskun.postman.google.account.service.GoogleLinkedAccountService;
import space.paraskun.postman.security.AuthenticationConsumer;
import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.AuthenticationService;

@Service
public class OAuthAuthenticationService implements AuthenticationService {
	private final AuthenticationConsumer consumer;
	private final GoogleLinkedAccountService accountService;

	public OAuthAuthenticationService(
			@Qualifier("consoleAuthenticationConsumer") AuthenticationConsumer consumer,
			@Autowired GoogleLinkedAccountService accountService
	) {
		this.consumer = consumer;
		this.accountService = accountService;
	}

	@Override
	public void authenticate(Object... params) {
		try {
			if (params.length == 2 && params[0] instanceof String code && params[1] instanceof String state) {
				OAuthCredential credential = getCredential(code);
				Userinfo userinfo = getUserinfo(credential);
				GoogleLinkedAccount account = new GoogleLinkedAccount(userinfo.getEmail(), credential, null, null);
				account = accountService.saveWithoutTemplates(account);
				consumer.onAuthenticationSuccess(account, state);
			} else
				throw new AuthenticationException("Incorrect authentication method.");
		} catch (AuthenticationException e) {
			consumer.onAuthenticationFailure(e);
		}
	}

	private OAuthCredential getCredential(String code) throws OAuthException {
		return null;
	}

	private Userinfo getUserinfo(OAuthCredential credential) throws OAuthException {
		return null;
	}
}
