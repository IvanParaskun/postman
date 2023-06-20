package space.paraskun.postman.messaging;

import space.paraskun.postman.account.Account;

/**
 * Sends given message using given account
 */
@FunctionalInterface
public interface MessageSender<T extends Account> {
	void send(T account, Message message) throws MessagingException;
}
