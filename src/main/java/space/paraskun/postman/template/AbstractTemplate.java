package space.paraskun.postman.template;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter @Setter
public abstract class AbstractTemplate {
    private final String title;

    public AbstractTemplate(String title) {
        if (title == null)
            throw new NullPointerException();

        this.title = title;
    }

    public abstract Message toMessage(Map<Object, Object> data)
            throws IncompatibleDataSetException;
}
