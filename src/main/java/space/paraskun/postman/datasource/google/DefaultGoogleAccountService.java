package space.paraskun.postman.datasource.google;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.paraskun.postman.account.google.GoogleAccount;
import space.paraskun.postman.auth.google.GoogleAuthService;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultGoogleAccountService implements GoogleAccountService {
	private final GoogleAccountRepository googleAccountRepository;
	private final GoogleAuthService googleAuthService;

	@Override
	public GoogleAccount findById(String id) {
		Optional<GoogleAccount> accountOptional = googleAccountRepository.findById(id);
		return accountOptional.map(this::refreshIfNotValid).orElse(null);
	}

	@Override
	public GoogleAccount findByEmail(String email) {
		Optional<GoogleAccount> accountOptional = googleAccountRepository.findByEmail(email);
		return accountOptional.map(this::refreshIfNotValid).orElse(null);
	}

	private GoogleAccount refreshIfNotValid(GoogleAccount account) {
		if (account.getCredential().isAccessValid())
			return account;

		if (googleAuthService.refreshCredential(account))
			return account;

		googleAccountRepository.delete(account);
		return null;
	}

	@Override
	public GoogleAccount save(GoogleAccount googleAccount) {
		return googleAccountRepository.save(googleAccount);
	}

	@Override
	public void delete(GoogleAccount googleAccount) {
		googleAccountRepository.delete(googleAccount);
	}

	@Override
	public void deleteById(String id) {
		googleAccountRepository.deleteById(id);
	}

	@Override
	public void deleteByEmail(String email) {
		googleAccountRepository.deleteByEmail(email);
	}
}
