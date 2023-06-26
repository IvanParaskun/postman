package space.paraskun.postman.mailing.consumer;

import space.paraskun.postman.mailing.model.MailingProgress;

public interface MailingProgressConsumer {
    void onProgress(String state, MailingProgress progress);
    void onFail(String state, Throwable exception);
}
