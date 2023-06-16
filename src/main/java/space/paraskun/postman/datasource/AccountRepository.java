package space.paraskun.postman.datasource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.paraskun.postman.account.AbstractAccount;

@Repository
public interface AccountRepository extends CrudRepository<AbstractAccount, String> {
}
