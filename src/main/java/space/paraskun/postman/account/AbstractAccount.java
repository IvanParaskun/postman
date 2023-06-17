package space.paraskun.postman.account;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.redis.core.RedisHash;
import space.paraskun.postman.template.AbstractMessageTemplate;
import java.util.HashMap;
import java.util.Map;

@RedisHash("accounts") @Getter
public abstract class AbstractAccount {
	@Id private String id;
	private final Map<String, AbstractMessageTemplate> templates;

	@PersistenceCreator
	public AbstractAccount(Map<String, AbstractMessageTemplate> templates) {
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
