package space.paraskun.postman.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service @Scope("prototype")
@RequiredArgsConstructor
public class AuthenticationService<T extends Credential> {
	private final AbstractAuthenticationFlow<T> flow;

	public AbstractAuthenticationFlow<T> create(Object state, AuthenticationConsumer authenticationConsumer) {
		if (state == null || authenticationConsumer == null)
			throw new NullPointerException();

		return flow.create(state, authenticationConsumer);
	}

	public AbstractAuthenticationFlow<T> restore(Object state) throws SessionExpiredException {
		return flow.restore(state);
	}
}
