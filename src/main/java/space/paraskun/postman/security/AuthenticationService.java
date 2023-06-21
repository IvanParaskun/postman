package space.paraskun.postman.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import space.paraskun.postman.security.flow.AbstractAuthenticationFlow;
import space.paraskun.postman.security.flow.FlowDoesNotExistsException;

@Service
@Scope("prototype")
@RequiredArgsConstructor
public class AuthenticationService<T extends Credential> {
	private final AbstractAuthenticationFlow<T> flow;

	public AbstractAuthenticationFlow<T> initialize(
			Object state,
			AuthenticationConsumer authenticationConsumer,
			Redirect redirect
	) {
		if (state == null || authenticationConsumer == null || redirect == null)
			throw new NullPointerException();

		return flow.init(state, authenticationConsumer, redirect).persist();
	}

	public AbstractAuthenticationFlow<T> restore(Object state) throws FlowDoesNotExistsException {
		return flow.restore(state);
	}
}
