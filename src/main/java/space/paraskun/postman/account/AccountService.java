package space.paraskun.postman.account;

import space.paraskun.postman.security.Credential;
import java.util.Optional;

public interface AccountService<T extends Credential> {
	Optional<Account<T>> findById(String id);
	Optional<Account<T>> findByCredentialIdentifier(String identifier);
	Account<T> save(Account<T> account);
	void delete(Account<T> account);
	void deleteById(String id);
}
