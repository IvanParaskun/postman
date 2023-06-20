package space.paraskun.postman.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationFlowRepository<T extends Credential>
		extends CrudRepository<AbstractAuthenticationFlow<T>, Object> {
}
