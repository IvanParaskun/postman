package space.paraskun.postman.security.model;

import lombok.Getter;

@Getter
public abstract class CredentialHolder<T extends Credential> {
    private T credential;
}
