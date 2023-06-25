package space.paraskun.postman.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service @Scope("prototype")
@RequiredArgsConstructor
public class AuthenticationService<T extends Credential> {
	private final AbstractAuthenticationFlow<T> flow;

	public AbstractAuthenticationFlow<T> create(String state, AuthenticationConsumer authenticationConsumer) {
		return flow.create(state, authenticationConsumer);
	}

	public AbstractAuthenticationFlow<T> restore(String state) throws SessionExpiredException {
		return flow.restore(state);
	}
}
