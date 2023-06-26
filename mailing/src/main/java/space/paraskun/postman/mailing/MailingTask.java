package space.paraskun.postman.mailing;

import space.paraskun.postman.mailing.consumer.MailingProgressConsumer;
import space.paraskun.postman.security.model.Credential;

public class MailingTask<T extends Credential> implements Runnable {
    private final String state;
    private final MailingProgressConsumer progressConsumer;

    public MailingTask(String state, MailingProgressConsumer progressConsumer) {
        this.state = state;
        this.progressConsumer = progressConsumer;
    }

    @Override
    public void run() {

    }
}
