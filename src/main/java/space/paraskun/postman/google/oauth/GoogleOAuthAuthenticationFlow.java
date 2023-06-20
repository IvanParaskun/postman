package space.paraskun.postman.google.oauth;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.stereotype.Service;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.security.AuthenticationConsumer;
import space.paraskun.postman.security.OAuthCredential;
import space.paraskun.postman.security.Redirect;

@Service("googleAuthenticationFlow")
@NoArgsConstructor
public class GoogleOAuthAuthenticationFlow extends AbstractAuthenticationFlow<OAuthCredential> {
	@PersistenceCreator
	public GoogleOAuthAuthenticationFlow(
			Object state,
			AuthenticationConsumer authenticationConsumer,
			Redirect redirect)
	{
		super(state, authenticationConsumer, redirect);
	}

	@Override
	public String getAuthenticationUrl() {
		return null;
	}

	@Override
	public void authenticate(String... data) {

	}
}
