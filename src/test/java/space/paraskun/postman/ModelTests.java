package space.paraskun.postman;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import space.paraskun.postman.model.Account;
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
        account.getCredential().refreshAccess("refreshed", 3600);
        assert account.getCredential().getAccess().equals("refreshed");
    }

    @Test
    public void saveTemplateLimitReached() {
        account.saveTemplate(new DefaultTemplate("Template 1"));
        account.saveTemplate(new DefaultTemplate("Template 2"));
        account.saveTemplate(new DefaultTemplate("Template 3"));
        assert account.saveTemplate(new DefaultTemplate("Template 4")) == null;
        assert account.templatesCount() == 3;
    }

    @Test
    public void removeTemplate() {
        account.saveTemplate(new DefaultTemplate("To remove"));
        account.removeTemplate("To remove");
        assert account.template("To remove") == null;
    }
}
