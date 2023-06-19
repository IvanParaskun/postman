package space.paraskun.postman.account;

import java.util.Optional;

public interface AccountService {
	Optional<? extends AbstractAccount> findById(String id);
	void deleteById(String id);
}
