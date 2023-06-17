package space.paraskun.postman.messaging;

import space.paraskun.postman.account.AbstractAccount;

/**
 * Sends given message using given account
 */
@FunctionalInterface
public interface MessageSender<T extends AbstractAccount> {
	void send(T account, Message message) throws MessagingException;
}
