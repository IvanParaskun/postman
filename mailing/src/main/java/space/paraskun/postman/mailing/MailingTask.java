package space.paraskun.postman.mailing;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import space.paraskun.postman.mailing.consumer.MailingProgressConsumer;
import space.paraskun.postman.mailing.model.MailingProgress;
import space.paraskun.postman.mailing.provider.MailingProvider;
import space.paraskun.postman.security.model.Credential;
import java.util.List;
import java.util.Map;

@Component @Scope("prototype") @Setter
public class MailingTask<T extends Credential> implements Runnable {
    private final MailingProvider<T> provider;

    private T credential;
    private AbstractMessageTemplate template;
    private List<Map<Object, Object>> data;
    private String state;
    private MailingProgressConsumer progressConsumer;

    @Autowired
    public MailingTask(MailingProvider<T> provider) {
        this.provider = provider;
    }

    protected MailingTask<T> initialize(
          T credential,
          AbstractMessageTemplate template,
          List<Map<Object, Object>> data,
          String state,
          MailingProgressConsumer consumer
    ) {
        if (credential == null || state == null || consumer == null || data == null || template == null)
            throw new NullPointerException();

        this.credential = credential;
        this.template = template;
        this.data = data;
        this.state = state;
        this.progressConsumer = consumer;
        return this;
    }

    @Override
    public void run() {
        if (credential == null)
            throw new NullPointerException();

        int current = 0;
        int goal = data.size();

        try {
            progressConsumer.onProgress(state, new MailingProgress(goal, current));

            for (Map<Object, Object> d: data) {
                provider.send(credential, template.toMessage(d));
                progressConsumer.onProgress(state, new MailingProgress(goal, ++current));
            }
        } catch (Throwable e) {
            progressConsumer.onFail(state, e);
        }
    }
}
