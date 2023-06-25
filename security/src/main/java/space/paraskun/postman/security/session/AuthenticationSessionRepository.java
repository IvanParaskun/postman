package space.paraskun.postman.security.session;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationSessionRepository extends CrudRepository<AuthenticationSession, String> {
}
