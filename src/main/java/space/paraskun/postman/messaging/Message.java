package space.paraskun.postman.messaging;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Message {
	private String sender;
	private String recipient;
	private String subject;
	private String text;
}