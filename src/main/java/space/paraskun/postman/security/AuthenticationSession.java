package space.paraskun.postman.security;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import space.paraskun.postman.ContextHolder;

@RedisHash("auth:sessions") @Getter
public class AuthenticationSession {
	private final @Id String state;
	private final @Transient AuthenticationConsumer authenticationConsumer;
	private final String consumerClassName;
	private final @TimeToLive long timeToLive;


	public AuthenticationSession(String state, AuthenticationConsumer consumer) {
		if (state == null || consumer == null)
			throw new NullPointerException();

		this.state = state;
		this.authenticationConsumer = consumer;
		this.consumerClassName = consumer.getClass().getCanonicalName();
		this.timeToLive = 300;
	}

	@PersistenceCreator
	public AuthenticationSession(String state, String consumerClassName, long timeToLive)
			throws ClassNotFoundException {

		Class<? extends AuthenticationConsumer> consumerClass = Class.forName(consumerClassName)
				.asSubclass(AuthenticationConsumer.class);

		this.state = state;
		this.authenticationConsumer = ContextHolder.getBean(consumerClass);
		this.consumerClassName = consumerClassName;
		this.timeToLive = timeToLive;
	}
}
