package space.paraskun.postman.security;

public interface CredentialHolder<T extends Credential> {
	T getCredential();
}
