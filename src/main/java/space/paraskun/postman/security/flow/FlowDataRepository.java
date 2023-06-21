package space.paraskun.postman.security.flow;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowDataRepository extends CrudRepository<FlowData, Object> {
}
