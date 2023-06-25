package space.paraskun.postman.security.consumer;

import org.springframework.stereotype.Component;
import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.model.Credential;
import space.paraskun.postman.security.model.CredentialHolder;

@Component(value = "consoleAuthenticationConsumer")
public class ConsoleAuthenticationConsumer implements AuthenticationConsumer {
    @Override
    public void onAuthenticationSuccess(String state, CredentialHolder<? extends Credential> credentialHolder) {
        System.out.printf(
                "Authentication success:\n\tState: %s\n\tCredential: %s\n\n",
                state,
                credentialHolder.getCredential()
        );
    }

    @Override
    public void onAuthenticationFailure(String state, AuthenticationException exception) {
        System.out.printf(
                "Authentication failed:\n\tState: %s\n\tCause: %s\n\n",
                state,
                exception.toString()
        );
    }

    @Override
    public String getRedirectUrl() {
        return "https://github.com/IvanParaskun/postman";
    }
}
