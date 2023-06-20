package space.paraskun.postman.account;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.redis.core.RedisHash;
import space.paraskun.postman.security.Credential;
import space.paraskun.postman.security.CredentialHolder;
import space.paraskun.postman.template.AbstractMessageTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RedisHash("accounts")
public class Account<T extends Credential> implements CredentialHolder<T> {
	@Id private String id;
	private final T credential;
	private final Map<String, AbstractMessageTemplate> templates;

	@PersistenceCreator
	public Account(String id, T credential, Map<String, AbstractMessageTemplate> templates) {
		this(credential, templates);
		this.id = id;
	}

	public Account(T credential, Map<String, AbstractMessageTemplate> templates) {
		if (credential == null)
			throw new NullPointerException();

		this.credential = credential;
		this.templates = templates == null ? new HashMap<>() : templates;
	}

	@Override
	public T getCredential() {
		return this.credential;
	}

	public List<AbstractMessageTemplate> listTemplates() {
		return templates.values().stream().toList();
	}

	public AbstractMessageTemplate getTemplate(String title) {
		return templates.get(title);
	}

	public AbstractMessageTemplate saveTemplate(AbstractMessageTemplate template)
			throws TemplateLimitReachedException {
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
