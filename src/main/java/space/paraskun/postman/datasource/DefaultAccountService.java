package space.paraskun.postman.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import space.paraskun.postman.auth.AuthService;
import space.paraskun.postman.auth.exception.RefreshTokenExpiredException;
import space.paraskun.postman.model.AbstractTemplate;
import space.paraskun.postman.model.Account;
import space.paraskun.postman.model.exception.TemplateLimitReachedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService {
    private final AccountRepository accountRepository;
    private final AuthService authService;

    @Override
    public Account get(String email) {
        assert email != null;
        Optional<Account> optionalAccount = accountRepository.findById(email);

        if (optionalAccount.isEmpty())
            return null;

        Account account = optionalAccount.get();

        if (!account.getCredential().isAccessValid())
            try {
                Pair<String, Long> refreshedAccessToken = authService
                        .refreshAccessToken(account.getCredential().getRefreshToken());
                account.getCredential().refresh(refreshedAccessToken.getFirst(), refreshedAccessToken.getSecond());
                return accountRepository.save(account);
            } catch (RefreshTokenExpiredException e) {
                return null;
            }

        return account;
    }

    @Override
    public Account createOrReplaceCredential(Account account) {
        assert account != null;

        Optional<Account> prevOpt = accountRepository.findById(account.getEmail());

        if (prevOpt.isPresent()) {
            Account prev = prevOpt.get();
            prev.getCredential().replaceAll(
                    account.getCredential().getAccessToken(),
                    account.getCredential().getRefreshToken(),
                    account.getCredential().getExpiresAt()
            );
            return accountRepository.save(prev);
        }

        return accountRepository.save(account);
    }

    @Override
    public Account saveTemplate(String email, AbstractTemplate template) throws TemplateLimitReachedException {
        assert email != null && template != null;
        Account account = get(email);

        if (account == null)
            return null;

        return accountRepository.save(account.saveTemplate(template));
    }

    @Override
    public Account removeTemplate(String email, String title) {
        assert email != null && title != null;
        Account account = get(email);

        if (account == null)
            return null;

        account.removeTemplate(title);
        return accountRepository.save(account);
    }

    @Override
    public void remove(String email) {
        accountRepository.deleteById(email);
    }
}
