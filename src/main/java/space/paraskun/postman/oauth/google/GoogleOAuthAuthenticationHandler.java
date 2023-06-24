package space.paraskun.postman.oauth.google;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.paraskun.postman.security.AuthenticationService;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.oauth.OAuthCredential;
import space.paraskun.postman.security.SessionExpiredException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GoogleOAuthAuthenticationHandler {
	private final AuthenticationService<OAuthCredential> authenticationService;

	@GetMapping("/")
	public String onResponse(@PathVariable("code") String code, @PathVariable("state") Object state) {
		try {
			AbstractAuthenticationFlow<OAuthCredential> flow = authenticationService.restore(state);
			flow.authenticate(code);
			return "redirect:" + flow.getData().getRedirect().getUrl();
		} catch (SessionExpiredException e) {
			return "Authentication timeout";
		}
	}
}
