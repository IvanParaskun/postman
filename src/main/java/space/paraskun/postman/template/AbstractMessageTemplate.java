package space.paraskun.postman.template;

import lombok.Getter;
import lombok.Setter;
import space.paraskun.postman.messaging.Message;
import java.util.Map;

@Getter @Setter
public abstract class AbstractMessageTemplate {
    private final String title;

    public AbstractMessageTemplate(String title) {
        if (title == null)
            throw new NullPointerException();

        this.title = title;
    }

    public abstract Message toMessage(Map<Object, Object> data)
            throws IncompatibleDataSetException;
}
