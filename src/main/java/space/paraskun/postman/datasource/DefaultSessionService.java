package space.paraskun.postman.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.paraskun.postman.model.Account;
import space.paraskun.postman.session.Session;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultSessionService implements SessionService {
    private final AccountService accountService;
    private final SessionRepository sessionRepository;

    @Override
    public Session get(long telegramId) {
        Optional<Session> sessionOpt = sessionRepository.findById(telegramId);

        if (sessionOpt.isEmpty())
            return null;

        Session session = sessionOpt.get();

        if (session.)
    }

    @Override
    public Session get(String email) {
        return null;
    }

    @Override
    public Session createIfNotExists(long telegramId, Account account) {
        return null;
    }

    @Override
    public void shutdown(Session session) {

    }
}
