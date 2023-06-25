package space.paraskun.postman.account.google;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.paraskun.postman.account.Account;
import space.paraskun.postman.account.AccountRepository;
import space.paraskun.postman.account.AccountService;
import space.paraskun.postman.oauth.google.GoogleOAuth2Credential;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleAccountService implements AccountService<GoogleOAuth2Credential> {
    private final AccountRepository<GoogleOAuth2Credential> repository;

    @Override
    public Optional<Account<GoogleOAuth2Credential>> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Account<GoogleOAuth2Credential>> findByCredentialIdentifier(String identifier) {
        Account<GoogleOAuth2Credential> account = repository.findAccountByCredentialIdentifier(identifier);
        return account == null ? Optional.empty() : Optional.of(account);
    }

    @Override
    public Account<GoogleOAuth2Credential> save(Account<GoogleOAuth2Credential> account) {
        return repository.save(account);
    }

    @Override
    public void delete(Account<GoogleOAuth2Credential> account) {
        repository.delete(account);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
