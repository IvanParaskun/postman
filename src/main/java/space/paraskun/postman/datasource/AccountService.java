package space.paraskun.postman.datasource;

import space.paraskun.postman.model.AbstractTemplate;
import space.paraskun.postman.model.Account;
import space.paraskun.postman.model.exception.TemplateLimitReachedException;

public interface AccountService {
    Account get(String email);
    Account createOrReplaceCredential(Account account);
    Account saveTemplate(String email, AbstractTemplate template) throws TemplateLimitReachedException;
    Account removeTemplate(String email, String title);
    void remove(String email);
}
