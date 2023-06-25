package space.paraskun.postman.security.model;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public abstract class Credential {
    private final String identifier;

    public Credential(String identifier) {
        if (identifier == null)
            throw new NullPointerException();

        this.identifier = identifier;
    }
}
