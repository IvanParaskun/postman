package space.paraskun.postman.google.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceCreator;
import space.paraskun.postman.account.AbstractAccount;
import space.paraskun.postman.template.AbstractMessageTemplate;
import java.util.Map;

@Getter @Setter
public class GoogleLinkedAccount extends AbstractAccount {
	private final String email;
	private final OAuthCredential credential;
	private String spreadsheetId;

	@PersistenceCreator
	public GoogleLinkedAccount(
			String email,
			OAuthCredential credential,
			String spreadsheetId,
			Map<String, AbstractMessageTemplate> templates
	) {
		super(credential, templates);

		if (email == null)
			throw new NullPointerException();

		this.email = email;
		this.credential = credential;
		this.spreadsheetId = spreadsheetId;
	}
}
