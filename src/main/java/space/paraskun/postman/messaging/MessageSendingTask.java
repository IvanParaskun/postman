package space.paraskun.postman.messaging;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import space.paraskun.postman.account.AbstractAccount;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MessageSendingTask<T extends AbstractAccount> extends Thread {
	private final T account;
	private final List<Message> messages;
	private final MessageSender<T> messageSender;
	private final Consumer<Progress> onProgress;
	private final Consumer<Throwable> onError;

	@Override
	public void run() {
		int goal = messages.size();
		int current = 0;

		try {
			for (Message message: messages) {
				messageSender.send(account, message);
				onProgress.accept(new Progress(goal, ++current));
			}
		} catch (MessagingException e) {
			onError.accept(e);
		}
	}

	@AllArgsConstructor
	public static class Progress {
		public int goal;
		public int current;
	}
}
