package space.paraskun.postman.model;

import jakarta.mail.internet.MimeMessage;
import space.paraskun.postman.model.exception.IncompatibleDataSetException;
import java.util.Map;

public abstract class AbstractTemplate {
    protected final String title;
    protected String sheetName;

    protected AbstractTemplate(String title) {
        assert title != null;
        this.title = title;
    }

    public abstract MimeMessage toMessage(Map<Object, Object> data) throws IncompatibleDataSetException;
}
