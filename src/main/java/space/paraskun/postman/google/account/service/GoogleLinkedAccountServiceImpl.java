package space.paraskun.postman.google.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.paraskun.postman.google.account.GoogleLinkedAccount;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleLinkedAccountServiceImpl implements GoogleLinkedAccountService {
	private final GoogleLinkedAccountRepository repository;

	@Override
	public Optional<GoogleLinkedAccount> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Optional<GoogleLinkedAccount> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public GoogleLinkedAccount saveWithoutTemplates(GoogleLinkedAccount account) {
		Optional<GoogleLinkedAccount> prev = findByEmail(account.getEmail());
		prev.ifPresent(googleLinkedAccount -> {
			account.setId(googleLinkedAccount.getId());
			account.setTemplates(googleLinkedAccount.getTemplates());
		});
		return repository.save(account);
	}

	@Override
	public GoogleLinkedAccount saveWithTemplates(GoogleLinkedAccount account) {
		Optional<GoogleLinkedAccount> prev = findByEmail(account.getEmail());
		prev.ifPresent(googleLinkedAccount -> {
			account.setId(googleLinkedAccount.getId());
		});
		return repository.save(account);
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteByEmail(String email) {
		repository.deleteByEmail(email);
	}
}
