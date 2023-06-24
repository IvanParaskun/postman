package space.paraskun.postman.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("flows")
@AllArgsConstructor @Getter
public class AuthenticationSession {
	@Id private final Object state;
	private final AuthenticationConsumer authenticationConsumer;

	@TimeToLive
	private final long ttl = 300;
}
