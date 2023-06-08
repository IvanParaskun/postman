package space.paraskun.postman.templates;

import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import space.paraskun.postman.model.AbstractTemplate;
import java.util.Map;

public class DefaultTemplate extends AbstractTemplate {
    private String text;

    public DefaultTemplate(String title) {
        super(title);
    }

    @Override
    public Multipart getMessageContent(Map<Object, Object> data)
            throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();

        bodyPart.setContent(populateText(text, data), "text/plain");
        multipart.addBodyPart(bodyPart);

        return multipart;
    }

    private String populateText(String base, Map<Object, Object> data) {
        for (Map.Entry<Object, Object> entry: data.entrySet())
            base = base.replace(String.format("{%s}", entry.getKey()), entry.getValue().toString());

        return base;
    }
}
