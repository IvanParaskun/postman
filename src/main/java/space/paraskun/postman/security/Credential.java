package space.paraskun.postman.security;

import lombok.Getter;

@Getter
public abstract class Credential {
	private String identifier;

	abstract public void reload(Credential credential);

	protected void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
