package space.paraskun.postman.account;

import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.redis.core.RedisHash;
import space.paraskun.postman.security.Credential;
import space.paraskun.postman.template.AbstractMessageTemplate;
import java.util.HashMap;
import java.util.Map;

@RedisHash("accounts") @Getter @Setter
public abstract class AbstractAccount {
	@Id private String id;
	private final Credential credential;
	private Map<String, AbstractMessageTemplate> templates;

	@PersistenceCreator
	public AbstractAccount(String id, Credential credential, Map<String, AbstractMessageTemplate> templates) {
		this(credential, templates);
		this.id = id;
	}

	public AbstractAccount(Credential credential, Map<String, AbstractMessageTemplate> templates) {
		if (credential == null)
			throw new NullPointerException();

		this.credential = credential;
		this.templates = templates == null ? new HashMap<>() : templates;
	}

	public AbstractMessageTemplate getTemplate(String title) {
		return templates.get(title);
	}

	public AbstractMessageTemplate saveTemplate(AbstractMessageTemplate template) throws TemplateLimitReachedException {
		if (!templates.containsKey(template.getTitle()) && templates.size() == 3)
			throw new TemplateLimitReachedException("Template limit reached.");

		templates.put(template.getTitle(), template);
		return template;
	}

	public void removeTemplate(String title) {
		templates.remove(title);
	}

	public int getTemplatesCount() {
		return templates.size();
	}
}
