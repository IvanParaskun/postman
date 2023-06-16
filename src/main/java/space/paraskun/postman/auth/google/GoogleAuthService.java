package space.paraskun.postman.auth.google;

import space.paraskun.postman.account.google.GoogleAccount;

public interface GoogleAuthService {
	String getAuthUrl(String state);
	boolean refreshCredential(GoogleAccount account);
}
