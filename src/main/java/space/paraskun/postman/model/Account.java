package space.paraskun.postman.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.redis.core.RedisHash;
import space.paraskun.postman.model.exception.TemplateLimitReachedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RedisHash("accounts")
@Data @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
    @Id @EqualsAndHashCode.Include
    private final String email;
    private final String spreadsheetId;
    private final Credential credential;
    private final Map<String, AbstractTemplate> templates;

    @PersistenceCreator
    public Account(String email, String spreadsheetId, Credential credential, Map<String, AbstractTemplate> templates) {
        assert email != null && spreadsheetId != null && credential != null;
        this.email = email;
        this.spreadsheetId = spreadsheetId;
        this.credential = credential;
        this.templates = templates == null ? new HashMap<>() : templates;
    }

    // Templates
    public AbstractTemplate getTemplate(String title) {
        return templates.get(title);
    }

    public int getTemplatesCount() {
        return templates.size();
    }

    public Account saveTemplate(AbstractTemplate template) throws TemplateLimitReachedException {
        if (!templates.containsKey(template.title) && templates.size() == 3)
            throw new TemplateLimitReachedException("Template limit.");

        templates.put(template.title, template);
        return this;
    }

    public void removeTemplate(String title) {
        this.templates.remove(title);
    }


    @Getter
    public static class Credential {
        private String accessToken;
        private String refreshToken;
        private Date expiresAt;

        @PersistenceCreator
        private Credential(String accessToken, String refreshToken, Date expiresAt) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresAt = expiresAt;
        }

        /**
         * @param expiresIn Time in seconds before access token becomes invalid.
         */
        public Credential(String accessToken, String refreshToken, long expiresIn) {
            assert accessToken != null && refreshToken != null;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresAt = new Date(System.currentTimeMillis() + expiresIn * 1000);
        }

        /**
         *
         * @return Whether access token valid or not.
         */
        public boolean isAccessValid() {
            return expiresAt.after(new Date(System.currentTimeMillis()));
        }

        /**
         * Refresh access token and expiration date. Used when access token expires.
         * @param accessToken New access token, received using refresh token.
         * @param expiresIn New delay in seconds, received using refresh token.
         */
        public void refresh(String accessToken, long expiresIn) {
            assert accessToken != null;
            this.accessToken = accessToken;
            this.expiresAt = new Date(System.currentTimeMillis() + expiresIn * 1000);
        }

        /**
         * Replace all credential fields (e.g. reauthenticate)
         * @param accessToken Initial access token, received from Google.
         * @param refreshToken Initial refresh token, received from Google.
         * @param expiresAt Initial date of access expiration.
         */
        public void replaceAll(String accessToken, String refreshToken, Date expiresAt) {
            assert accessToken != null && refreshToken != null;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresAt = expiresAt;
        }
    }
}
