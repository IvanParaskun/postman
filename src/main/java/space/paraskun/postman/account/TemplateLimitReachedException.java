package space.paraskun.postman.account;

public class TemplateLimitReachedException extends Exception {
    public TemplateLimitReachedException(String message) {
        super(message);
    }
}
