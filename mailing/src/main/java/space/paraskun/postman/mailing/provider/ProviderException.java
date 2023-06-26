package space.paraskun.postman.mailing.provider;

import space.paraskun.postman.mailing.MailingException;

public class ProviderException extends MailingException {
    public ProviderException() {
        super();
    }

    public ProviderException(String message) {
        super(message);
    }
}
