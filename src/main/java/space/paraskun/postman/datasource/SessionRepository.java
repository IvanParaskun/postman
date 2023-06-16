package space.paraskun.postman.datasource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.paraskun.postman.session.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    Session findSessionByAccount_Email(String email);
}
