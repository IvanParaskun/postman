package space.paraskun.postman.security.model;

public interface CredentialHolderFactory<T extends Credential> {
    CredentialHolder<T> create(T credential);
}
