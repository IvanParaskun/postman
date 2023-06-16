package space.paraskun.postman.datasource.google;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.paraskun.postman.account.google.GoogleAccount;
import java.util.Optional;

@Repository
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, String> {
	Optional<GoogleAccount> findByEmail(String email);
	void deleteByEmail(String email);
}
