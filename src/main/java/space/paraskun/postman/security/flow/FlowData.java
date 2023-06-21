package space.paraskun.postman.security.flow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import space.paraskun.postman.security.AuthenticationConsumer;
import space.paraskun.postman.security.Redirect;

@RedisHash("flows")
@AllArgsConstructor @Getter
public class FlowData {
	@Id private final Object state;
	private final AuthenticationConsumer authenticationConsumer;
	private final Redirect redirect;
	@TimeToLive private final long ttl = 300;
}
