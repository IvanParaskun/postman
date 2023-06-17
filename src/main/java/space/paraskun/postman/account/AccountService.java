package space.paraskun.postman.account;

public interface AccountService {
	boolean exists(String id);
	AbstractAccount findById(String id);
	AbstractAccount save(AbstractAccount account);
	void delete(String id);
}
