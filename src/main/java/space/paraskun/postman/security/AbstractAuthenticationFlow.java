package space.paraskun.postman.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component @Scope("prototype")
public abstract class AbstractAuthenticationFlow<T extends Credential> {
	private AuthenticationSessionRepository repository;
	private AuthenticationSession session;

	public abstract String getAuthenticationUrl();
	public abstract void authenticate(String... data);

	public String getState() {
		return this.session.getState();
	}

	public AuthenticationConsumer getAuthenticationConsumer() {
		return this.session.getAuthenticationConsumer();
	}

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

	@Autowired
	public void setRepository(AuthenticationSessionRepository repository) {
		this.repository = repository;
	}
}
