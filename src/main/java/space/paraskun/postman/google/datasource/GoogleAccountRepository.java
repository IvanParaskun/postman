package space.paraskun.postman.google.datasource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.paraskun.postman.google.model.GoogleAccount;
import java.util.Optional;

@Repository
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, String> {
	Optional<GoogleAccount> findByEmail(String email);
	void deleteByEmail(String email);
	boolean existsByEmail(String email);
}
