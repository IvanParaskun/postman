package space.paraskun.postman;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.paraskun.postman.account.AbstractAccount;
import space.paraskun.postman.account.google.GoogleAccount;
import space.paraskun.postman.datasource.AccountRepository;

@SpringBootTest
public class AccountRepositoryTests {
	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void saveGoogleAccount() {
		AbstractAccount account = new GoogleAccount(
				"test@email.com",
				"spreadsheetId",
				new GoogleAccount.Credential("access", "refresh", 3600),
				null
		);

		account = accountRepository.save(account);
		assert account.getId() != null;

		accountRepository.deleteById(account.getId());
	}
}
