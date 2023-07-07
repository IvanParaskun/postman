package space.paraskun.postman.mailing.consumer;

import org.springframework.stereotype.Service;
import space.paraskun.postman.mailing.MailingException;
import space.paraskun.postman.mailing.model.MailingProgress;

@Service
public class ConsoleMailingProgressConsumer implements MailingProgressConsumer {
	@Override
	public void onProgress(String state, MailingProgress progress) {
		System.out.printf(
				"Mailing progress:\n\tState: %s\n\tProgress:\n\t\tGoal: %d\n\t\tCurrent: %d\n\n",
				state, progress.goal(), progress.current()
		);
	}


	@Override
	public void onFail(String state, Throwable exception) {
		System.out.printf("Mailing fail:\n\tState: %s\n\tCause: %s\n\n", state, exception.toString());
	}
}
