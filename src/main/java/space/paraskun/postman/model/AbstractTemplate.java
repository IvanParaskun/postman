package space.paraskun.postman.model;

import jakarta.mail.internet.MimeMessage;
import space.paraskun.postman.model.exception.IncompatibleDataSetException;

import java.util.Map;

public abstract class AbstractTemplate {
    protected String title;
    protected String sheetName;

    public abstract MimeMessage toMessage(Map<Object, Object> data) throws IncompatibleDataSetException;
}
