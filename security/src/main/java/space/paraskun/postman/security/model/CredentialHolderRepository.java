package space.paraskun.postman.security.model;

public interface CredentialHolderRepository<T extends Credential> {
    CredentialHolder<T> findCredentialHolderByCredentialIdentifier(String identifier);
    CredentialHolder<T> save(CredentialHolder<T> holder);
}
