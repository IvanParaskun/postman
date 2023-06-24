package space.paraskun.postman.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Getter @RequiredArgsConstructor
@Component @Scope("prototype")
public abstract class AbstractAuthenticationFlow<T extends Credential> {
	private final AuthenticationSessionRepository repository;
	private AuthenticationSession session;

	public abstract String getAuthenticationUrl();
	public abstract void authenticate(String... data);

	protected AbstractAuthenticationFlow<T> create(Object state, AuthenticationConsumer consumer) {
		this.session = new AuthenticationSession(state, consumer);
		this.persist();
		return this;
	}

	private void persist() {
		if (this.session == null)
			throw new NullPointerException();

		repository.save(this.session);
	}

	protected AbstractAuthenticationFlow<T> restore(Object state) throws SessionExpiredException {
		Optional<AuthenticationSession> optional = repository.findById(state);

		if (optional.isEmpty())
			throw new SessionExpiredException();

		this.session = optional.get();
		return this;
	}
}
