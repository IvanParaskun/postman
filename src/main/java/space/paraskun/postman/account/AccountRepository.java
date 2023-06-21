package space.paraskun.postman.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.paraskun.postman.security.Credential;

@Repository
public interface AccountRepository<T extends Credential> extends CrudRepository<Account<T>, String> {
	Account<T> findAccountByCredentialIdentifier(String identifier);
}
