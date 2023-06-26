package space.paraskun.postman.mailing;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import space.paraskun.postman.mailing.consumer.MailingProgressConsumer;
import space.paraskun.postman.security.model.Credential;
import java.util.List;
import java.util.Map;

@Service @Scope("prototype")
@RequiredArgsConstructor
public class MailingService<T extends Credential> {
	private final MailingTask<T> mailingTask;

	public MailingTask<T> createTask(
			T credential,
			AbstractMessageTemplate template,
			List<Map<Object, Object>> data,
			String state,
			MailingProgressConsumer consumer
	) {
		return mailingTask.initialize(
				credential, template, data, state, consumer
		);
	}
}
