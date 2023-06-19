package space.paraskun.postman.google.account.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.paraskun.postman.google.account.GoogleLinkedAccount;
import java.util.Optional;

@Repository
public interface GoogleLinkedAccountRepository extends CrudRepository<GoogleLinkedAccount, String> {
	Optional<GoogleLinkedAccount> findByEmail(String email);
	void deleteByEmail(String email);
}
