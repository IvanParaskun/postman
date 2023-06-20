package space.paraskun.postman.security;

public interface OAuthCredential extends Credential {
	String getAccessToken();
}
