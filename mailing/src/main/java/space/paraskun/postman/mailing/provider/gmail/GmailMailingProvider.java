package space.paraskun.postman.mailing.provider.gmail;

import com.google.api.services.gmail.Gmail;
import org.springframework.stereotype.Service;
import space.paraskun.postman.mailing.model.Message;
import space.paraskun.postman.mailing.provider.MailingProvider;
import space.paraskun.postman.mailing.provider.ProviderException;
import space.paraskun.postman.security.oauth2.google.GoogleCredential;

@Service
public class GmailMailingProvider implements MailingProvider<GoogleCredential> {
	@Override
	public void send(GoogleCredential credential, Message message) throws ProviderException {

	}
}
