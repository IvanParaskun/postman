package space.paraskun.postman.google.datasource;

import space.paraskun.postman.google.model.GoogleAccount;

public interface GoogleAccountService {
	GoogleAccount findById(String id);
	GoogleAccount findByEmail(String email);
	GoogleAccount save(GoogleAccount googleAccount);
	boolean exists(String email);
	void delete(GoogleAccount googleAccount);
	void deleteById(String id);
	void deleteByEmail(String email);
}
