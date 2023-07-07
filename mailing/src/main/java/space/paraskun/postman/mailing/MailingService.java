package space.paraskun.postman.mailing;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import space.paraskun.postman.mailing.consumer.MailingProgressConsumer;
import space.paraskun.postman.mailing.model.MailingProgress;
import space.paraskun.postman.mailing.provider.MailingProvider;
import space.paraskun.postman.security.model.Credential;
import java.util.List;
import java.util.Map;

@Service @Scope("prototype")
@RequiredArgsConstructor
public class MailingService<T extends Credential> {
	private final MailingProvider<T> provider;
	private T credential;
	private String state;

	public MailingService<T> credential(T credential) {
		this.credential = credential;
		return this;
	}

	public MailingService<T> state(String state) {
		this.state = state;
		return this;
	}

	public void send(
			AbstractMessageTemplate template,
			List<Map<Object, Object>> data,
			MailingProgressConsumer consumer
	) {
		if (credential == null)
			throw new NullPointerException();

		Thread thread = new Thread(new Task(template, data, consumer));
		thread.start();
	}

	@AllArgsConstructor
	public class Task implements Runnable {
		private final AbstractMessageTemplate template;
		private final List<Map<Object, Object>> data;
		private final MailingProgressConsumer consumer;

		@Override
		public void run() {
			int current = 0;
			int goal = data.size();

			try {
				consumer.onProgress(state, new MailingProgress(goal, current));

				for (Map<Object, Object> d: data) {
					provider.send(credential, template.toMessage(d));
					consumer.onProgress(state, new MailingProgress(goal, ++current));
				}
			} catch (Throwable e) {
				consumer.onFail(state, e);
			}
		}
	}
}
