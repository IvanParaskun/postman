package space.paraskun.postman.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.index.Indexed;

@Getter @ToString
public abstract class Credential {
    private final @Indexed String identifier;

    public Credential(String identifier) {
        if (identifier == null)
            throw new NullPointerException();

        this.identifier = identifier;
    }
}
