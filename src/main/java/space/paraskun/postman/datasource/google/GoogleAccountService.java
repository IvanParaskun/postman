package space.paraskun.postman.datasource.google;

import space.paraskun.postman.account.google.GoogleAccount;

public interface GoogleAccountService {
	GoogleAccount findById(String id);
	GoogleAccount findByEmail(String email);
	GoogleAccount save(GoogleAccount googleAccount);
	void delete(GoogleAccount googleAccount);
	void deleteById(String id);
	void deleteByEmail(String email);
}
