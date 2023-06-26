package space.paraskun.postman.mailing;

public class IncompatibleDataException extends MailingException {
    public IncompatibleDataException() {
        super();
    }

    public IncompatibleDataException(String message) {
        super(message);
    }
}
