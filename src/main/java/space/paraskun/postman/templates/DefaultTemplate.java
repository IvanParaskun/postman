package space.paraskun.postman.templates;

import jakarta.mail.internet.MimeMessage;
import space.paraskun.postman.model.AbstractTemplate;
import space.paraskun.postman.model.exception.IncompatibleDataSetException;
import java.util.Map;

public class DefaultTemplate extends AbstractTemplate {
    private String text;
    private String from;

    public DefaultTemplate(String title) {
        super(title);
    }

    @Override
    public MimeMessage toMessage(Map<Object, Object> data) throws IncompatibleDataSetException {
        return null;
    }
}
