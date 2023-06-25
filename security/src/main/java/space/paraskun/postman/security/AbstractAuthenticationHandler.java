package space.paraskun.postman.security;

import org.springframework.stereotype.Component;
import space.paraskun.postman.security.model.Credential;
import space.paraskun.postman.security.session.SessionExpiredException;

@Component
public abstract class AbstractAuthenticationHandler<T extends Credential> {
    private final AbstractAuthenticationFlow<T> flow;

    public AbstractAuthenticationHandler(AbstractAuthenticationFlow<T> flow) {
        this.flow = flow;
    }

    protected void authenticate(String state, String... data) throws SessionExpiredException {
        flow.restore(state).authenticate(data);
    }

    protected String getRedirectUrl() {
        return flow.session.getAuthenticationConsumer().getRedirectUrl();
    }
}
