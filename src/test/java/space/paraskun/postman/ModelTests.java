package space.paraskun.postman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import space.paraskun.postman.model.Account;
import space.paraskun.postman.model.exception.TemplateLimitReachedException;
import space.paraskun.postman.templates.DefaultTemplate;

@SpringBootTest
public class ModelTests {
    private static Account account;

    @BeforeAll
    public static void createAccount() {
        account = Account.builder()
                .email("test@domain.com")
                .spreadsheetId("sheetId")
                .credential(new Account.Credential("access", "refresh", 3600))
                .build();
    }

    @Test
    public void refreshCredential() {
        account.getCredential().refresh("refreshed", 3600);
        assert account.getCredential().getAccessToken().equals("refreshed");
    }

    @Test
    public void saveTemplateLimitReached() {
        Assertions.assertDoesNotThrow(() -> {
            account.saveTemplate(new DefaultTemplate("Template 1"));
            account.saveTemplate(new DefaultTemplate("Template 2"));
            account.saveTemplate(new DefaultTemplate("Template 3"));
        });

        Assertions.assertThrows(TemplateLimitReachedException.class, () -> {
            account.saveTemplate(new DefaultTemplate("Template 4"));
        });

        assert account.getTemplatesCount() == 3;
        account.removeTemplate("Template 1");
    }

    @Test
    public void removeTemplate() {
        Assertions.assertDoesNotThrow(() -> {
            account.saveTemplate(new DefaultTemplate("To remove"));
            account.removeTemplate("To remove");
            assert account.getTemplate("To remove") == null;
        });
    }

    @Test
    public void credentialExpirationTestExpired() throws InterruptedException {
        Account account = Account.builder()
                .email("test@domain.com")
                .spreadsheetId("sheetId")
                .credential(new Account.Credential("access", "refresh", 2))
                .build();

        Thread.sleep(3000);
        assert !account.getCredential().isAccessValid();
    }

    @Test
    public void credentialExpirationTestNotExpired() throws InterruptedException {
        Account account = Account.builder()
                .email("test@domain.com")
                .spreadsheetId("sheetId")
                .credential(new Account.Credential("access", "refresh", 4))
                .build();

        Thread.sleep(3000);
        assert account.getCredential().isAccessValid();
    }
}
