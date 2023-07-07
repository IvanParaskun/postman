package space.paraskun.postman.security.model;

public interface CredentialHolderRepository<T extends Credential> {
    T findCredentialByIdentifier(String identifier);
}
