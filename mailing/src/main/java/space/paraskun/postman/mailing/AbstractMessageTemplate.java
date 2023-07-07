package space.paraskun.postman.mailing;

import lombok.Getter;
import space.paraskun.postman.mailing.model.Message;
import java.util.Map;

@Getter
public abstract class AbstractMessageTemplate {
    private String title;

    protected abstract Message toMessage(Map<Object, Object> data) throws IncompatibleDataException;
}
