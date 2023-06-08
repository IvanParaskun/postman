package space.paraskun.postman.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RedisHash("accounts")
@AllArgsConstructor
@Builder @ToString @Getter
public class Account {
    @Id private final String email;
    private final String spreadsheetId;
    private final Credential credential;
    private final Map<String, AbstractTemplate> templates = new HashMap<>();

    public AbstractTemplate template(String title) {
        return templates.get(title);
    }

    public int templatesCount() {
        return templates.size();
    }

    public AbstractTemplate saveTemplate(AbstractTemplate template) {
        if (!templates.containsKey(template.title) && templates.size() == 3)
            return null;

        templates.put(template.title, template);
        return template;
    }

    public void removeTemplate(String title) {
        this.templates.remove(title);
    }

    @AllArgsConstructor @Getter
    public static class Credential {
        private String access;
        private final String refresh;
        private Date expiresAt;

        public Credential(String access, String refresh, long expiresIn) {
            assert access != null && refresh != null;
            this.refresh = refresh;
            refreshAccess(access, expiresIn);
        }

        public void refreshAccess(String access, long expiresIn) {
            this.access = access;
            this.expiresAt = new Date(System.currentTimeMillis() + expiresIn);
        }
    }
}
