package space.paraskun.postman.security;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import space.paraskun.postman.security.consumer.AuthenticationConsumer;
import space.paraskun.postman.security.model.Credential;
import space.paraskun.postman.security.session.AuthenticationSession;
import space.paraskun.postman.security.session.AuthenticationSessionRepository;
import space.paraskun.postman.security.session.SessionExpiredException;
import java.util.Optional;

@Component @Scope("prototype")
public abstract class AbstractAuthenticationFlow<T extends Credential> {
	private final AuthenticationSessionRepository repository;
	protected AuthenticationSession session;

	public AbstractAuthenticationFlow(AuthenticationSessionRepository repository) {
		this.repository = repository;
	}

	public abstract String getAuthenticationUrl();

	protected abstract void authenticate(String... data);

	protected AbstractAuthenticationFlow<T> create(String state, AuthenticationConsumer consumer) {
		this.session = new AuthenticationSession(state, consumer);
		return this.persist();
	}

	private AbstractAuthenticationFlow<T> persist() {
		repository.save(this.session);
		return this;
	}

	protected AbstractAuthenticationFlow<T> restore(String state) throws SessionExpiredException {
		Optional<AuthenticationSession> optional = repository.findById(state);

		if (optional.isEmpty())
			throw new SessionExpiredException();

		this.session = optional.get();
		return this;
	}
}
