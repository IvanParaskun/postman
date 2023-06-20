package space.paraskun.postman.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
@RequiredArgsConstructor
public class AuthenticationService<T extends Credential> {
	private final AbstractAuthenticationFlow<T> flow;
	private final AuthenticationFlowRepository<T> repository;

	public AbstractAuthenticationFlow<T> initialize(
			Object state,
			AuthenticationConsumer authenticationConsumer,
			Redirect redirect
	) {
		if (state == null || authenticationConsumer == null || redirect == null)
			throw new NullPointerException();

		return repository.save(
				flow
						.state(state)
						.redirect(redirect)
						.authenticationConsumer(authenticationConsumer)
		);
	}
}
