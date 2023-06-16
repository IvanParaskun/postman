package space.paraskun.postman.datasource;

import space.paraskun.postman.model.Account;
import space.paraskun.postman.session.Session;

public interface SessionService {
    Session get(long telegramId);
    Session get(String email);
    Session createIfNotExists(long telegramId, Account account);
    void shutdown(Session session);
}
