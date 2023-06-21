package space.paraskun.postman.security.flow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import space.paraskun.postman.security.AuthenticationConsumer;
import space.paraskun.postman.security.Credential;
import space.paraskun.postman.security.Redirect;
import java.util.Optional;

@Getter @RequiredArgsConstructor
@Component @Scope("prototype")
public abstract class AbstractAuthenticationFlow<T extends Credential> {
	private final FlowDataRepository repository;
	private FlowData data;

	public AbstractAuthenticationFlow<T> init(
			Object state, AuthenticationConsumer consumer, Redirect redirect
	) {
		this.data = new FlowData(state, consumer, redirect);
		return this;
	}

	public AbstractAuthenticationFlow<T> restore(Object state)
			throws FlowDoesNotExistsException
	{
		Optional<FlowData> optional = repository.findById(state);

		if (optional.isEmpty())
			throw new FlowDoesNotExistsException("Can't find flow data for state: " + state);

		this.data = optional.get();
		return this;
	}

	public AbstractAuthenticationFlow<T> persist() {
		if (this.data == null)
			throw new NullPointerException("Initialize flow before persist.");

		repository.save(this.data);
		return this;
	}

	public abstract String getAuthenticationUrl();
	public abstract void authenticate(String... data);
}
