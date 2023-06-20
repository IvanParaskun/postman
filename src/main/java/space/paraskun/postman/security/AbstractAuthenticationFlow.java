package space.paraskun.postman.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter @NoArgsConstructor
@RedisHash("flows")
@Scope("prototype")
public abstract class AbstractAuthenticationFlow<T extends Credential> {
	@Id private Object state;
	@TimeToLive private final long ttl = 300;
	private AuthenticationConsumer authenticationConsumer;
	private Redirect redirect;

	@PersistenceCreator
	public AbstractAuthenticationFlow(
			Object state,
			AuthenticationConsumer authenticationConsumer,
			Redirect redirect)
	{
		this.state = state;
		this.authenticationConsumer = authenticationConsumer;
		this.redirect = redirect;
	}

	public abstract String getAuthenticationUrl();
	public abstract void authenticate(String... data);

	public AbstractAuthenticationFlow<T> state(Object state) {
		this.state = state;
		return this;
	}

	public AbstractAuthenticationFlow<T> authenticationConsumer(AuthenticationConsumer consumer) {
		this.authenticationConsumer = consumer;
		return this;
	}

	public AbstractAuthenticationFlow<T> redirect(Redirect redirect) {
		this.redirect = redirect;
		return this;
	}
}
