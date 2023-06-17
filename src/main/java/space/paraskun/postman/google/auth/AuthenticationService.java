package space.paraskun.postman.google.auth;

import space.paraskun.postman.google.model.GoogleAccount;
import space.paraskun.postman.google.datasource.GoogleAccountService;

public interface AuthenticationService {
	String getAuthUrl(String state);
	boolean refreshCredential(GoogleAccount account);
	GoogleAccount authenticate(String code, String state);

	void setAccountService(GoogleAccountService accountService);
}
