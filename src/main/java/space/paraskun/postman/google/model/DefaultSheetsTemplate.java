package space.paraskun.postman.google.model;

import lombok.Setter;
import space.paraskun.postman.template.IncompatibleDataSetException;
import space.paraskun.postman.template.Message;
import java.util.Map;

@Setter
public class DefaultSheetsTemplate extends AbstractSheetsTemplate {
	private String sender;
	private String subject;
	private String textPattern;

	public DefaultSheetsTemplate(String title, String sheetName) {
		super(title, sheetName);
	}

	@Override
	public Message toMessage(Map<Object, Object> data) throws IncompatibleDataSetException {
		String body = textPattern;
		String email = null;

		for (Map.Entry<Object, Object> entry: data.entrySet())
			if (entry.getKey().toString().equalsIgnoreCase("email"))
				email = entry.getValue().toString();
			else
				body = body.replace(String.format("{%s}", entry.getKey().toString()), entry.getValue().toString());

		if (email == null)
			throw new IncompatibleDataSetException("Email does not present.");

		return Message.builder().sender(sender)
				.subject(subject)
				.recipient(email)
				.text(body)
				.build();
	}
}
