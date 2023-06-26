package space.paraskun.postman.mailing.provider;

import space.paraskun.postman.mailing.model.Message;
import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.model.Credential;

@FunctionalInterface
public interface MailingProvider<T extends Credential> {
    void send(T credential, Message message) throws ProviderException, AuthenticationException;
}
