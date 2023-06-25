package space.paraskun.postman.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import space.paraskun.postman.security.consumer.AuthenticationConsumer;
import space.paraskun.postman.security.model.Credential;

@Service @Scope("prototype")
@RequiredArgsConstructor
public class AuthenticationService<T extends Credential> {
	private final AbstractAuthenticationFlow<T> flow;

	public AbstractAuthenticationFlow<T> createFlow(String state, AuthenticationConsumer authenticationConsumer) {
		return flow.create(state, authenticationConsumer);
	}
}
