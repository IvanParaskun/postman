package space.paraskun.postman.model;

import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import space.paraskun.postman.model.exception.IncompatibleDataSetException;
import java.util.Map;

public abstract class AbstractTemplate {
    protected final String title;
    protected String sheetName;
    protected String subject;
    protected String from;

    protected AbstractTemplate(String title) {
        assert title != null;
        this.title = title;
    }

    public abstract Multipart getMessageContent(Map<Object, Object> data)
            throws IncompatibleDataSetException, MessagingException;
}
