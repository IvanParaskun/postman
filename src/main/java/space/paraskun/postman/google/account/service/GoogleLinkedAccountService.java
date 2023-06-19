package space.paraskun.postman.google.account.service;

import space.paraskun.postman.account.AccountService;
import space.paraskun.postman.google.account.GoogleLinkedAccount;
import java.util.Optional;

public interface GoogleLinkedAccountService extends AccountService {
	Optional<GoogleLinkedAccount> findByEmail(String email);
	GoogleLinkedAccount saveWithoutTemplates(GoogleLinkedAccount account);
	GoogleLinkedAccount saveWithTemplates(GoogleLinkedAccount account);
	void deleteByEmail(String email);

	@Override
	Optional<GoogleLinkedAccount> findById(String id);
}
