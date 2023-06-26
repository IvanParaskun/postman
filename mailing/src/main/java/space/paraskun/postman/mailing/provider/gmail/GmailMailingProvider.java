package space.paraskun.postman.mailing.provider.gmail;

import com.google.api.services.gmail.Gmail;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import space.paraskun.postman.mailing.model.Message;
import space.paraskun.postman.mailing.provider.MailingProvider;
import space.paraskun.postman.mailing.provider.ProviderException;
import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.config.GoogleServices;
import space.paraskun.postman.security.oauth2.google.GoogleCredential;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class GmailMailingProvider implements MailingProvider<GoogleCredential> {
	private final GoogleServices googleServices;

	@Override
	public void send(GoogleCredential credential, Message message) throws ProviderException, AuthenticationException {
		Gmail gmail = googleServices.gmail(credential);

		try {
			com.google.api.services.gmail.model.Message msg = createMessage(credential.getIdentifier(), message);
			gmail.users().messages().send("me", msg);
		} catch (IOException | MessagingException e) {
			throw new ProviderException();
		}
	}

	private com.google.api.services.gmail.model.Message createMessage(String sender, Message message)
			throws MessagingException, IOException
	{
		MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));

		email.setFrom(new InternetAddress(sender, message.sender()));
		email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(message.destination()));
		email.setSubject(message.subject());
		email.setText(message.text());

		return toMessage(email);
	}

	private com.google.api.services.gmail.model.Message toMessage(MimeMessage email)
			throws MessagingException, IOException
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		email.writeTo(buffer);
		byte[] rawMessageBytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
		com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
		message.setRaw(encodedEmail);
		return message;
	}
}
