package space.paraskun.postman;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.paraskun.postman.datasource.AccountService;
import space.paraskun.postman.model.Account;
import space.paraskun.postman.model.exception.TemplateLimitReachedException;
import space.paraskun.postman.templates.DefaultTemplate;

import java.util.Date;

@SpringBootTest
public class AccountServiceTests {
    @Autowired
    private AccountService accountService;

    private static final Account ACCOUNT = Account.builder()
            .email("test@domain.com").spreadsheetId("testId")
            .credential(new Account.Credential("access", "refresh", 3600)).build();

    @AfterEach
    public void after() {
        accountService.remove(ACCOUNT.getEmail());
    }

    @Test
    public void createOk() {
        Account account = accountService.createOrReplaceCredential(ACCOUNT);
        assert account != null;
    }

    @Test
    public void createAlreadyExistsOk() {
        Account account = accountService.createOrReplaceCredential(ACCOUNT);
        assert account != null;

        Assertions.assertDoesNotThrow(() -> {
            ACCOUNT.saveTemplate(new DefaultTemplate("Template"));
        });

        account = accountService.createOrReplaceCredential(ACCOUNT);
        assert account.getTemplatesCount() == 0;
        ACCOUNT.removeTemplate("Template");
    }

    @Test
    public void saveTemplateOk() {
        Assertions.assertDoesNotThrow(() -> {
            Account account = accountService.createOrReplaceCredential(ACCOUNT);
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template"));
        });

        Account account = accountService.get(ACCOUNT.getEmail());
        assert account.getTemplatesCount() == 1;
    }

    @Test
    public void saveTemplateLimitReachedException() {
        Assertions.assertThrows(TemplateLimitReachedException.class, () -> {
            Account account = accountService.createOrReplaceCredential(ACCOUNT);
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template 1"));
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template 2"));
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template 3"));
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template 4"));
        });

        assert accountService.get(ACCOUNT.getEmail()).getTemplatesCount() == 3;
    }

    @Test
    public void updateTemplateOk() {
        Assertions.assertDoesNotThrow(() -> {
            Account account = accountService.createOrReplaceCredential(ACCOUNT);
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template 1"));
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template 1"));
        });

        assert accountService.get(ACCOUNT.getEmail()).getTemplatesCount() == 1;
    }

    @Test
    public void removeTemplateOk() {
        Assertions.assertDoesNotThrow(() -> {
            Account account = accountService.createOrReplaceCredential(ACCOUNT);
            accountService.saveTemplate(account.getEmail(), new DefaultTemplate("Template"));
            accountService.removeTemplate(account.getEmail(), "Template");
        });

        assert accountService.get(ACCOUNT.getEmail()).getTemplatesCount() == 0;
    }

    @Test
    public void removeTemplateDoesNotExistsOk() {
        Assertions.assertDoesNotThrow(() -> {
            Account account = accountService.createOrReplaceCredential(ACCOUNT);
            accountService.removeTemplate(account.getEmail(), "Template");
        });

        assert accountService.get(ACCOUNT.getEmail()).getTemplatesCount() == 0;
    }

    @Test
    public void refreshCredentialOnGetAfterExpirationOk() throws InterruptedException {
        Account account1 = Account.builder()
                .email("test@domain.com").spreadsheetId("spread1")
                .credential(new Account.Credential("access1", "refresh1", 2L)).build();

        accountService.createOrReplaceCredential(account1);

        Thread.sleep(5000);

        account1 = accountService.get("test@domain.com");
        assert account1.getCredential().getExpiresAt().after(new Date(System.currentTimeMillis()));
    }
}
