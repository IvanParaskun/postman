package space.paraskun.postman.datasource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.paraskun.postman.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
}
