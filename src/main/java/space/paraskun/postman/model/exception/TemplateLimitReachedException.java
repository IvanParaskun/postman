package space.paraskun.postman.model.exception;

public class TemplateLimitReachedException extends Exception {
    public TemplateLimitReachedException(String message) {
        super(message);
    }
}
