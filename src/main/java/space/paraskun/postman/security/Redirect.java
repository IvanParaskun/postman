package space.paraskun.postman.security;

import lombok.Getter;

@Getter
public enum Redirect {
	TELEGRAM("t.me/PostmanBot");

	Redirect(String url) {
		this.url = url;
	}

	private final String url;
}
