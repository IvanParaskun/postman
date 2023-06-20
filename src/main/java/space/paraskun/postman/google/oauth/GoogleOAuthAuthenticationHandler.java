package space.paraskun.postman.google.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.security.AuthenticationFlowRepository;
import space.paraskun.postman.security.OAuthCredential;

import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GoogleOAuthAuthenticationHandler {
	private final AuthenticationFlowRepository<OAuthCredential> repository;

	@GetMapping
	public String onResponse(@PathVariable("code") String code, @PathVariable("state") Object state) {
		Optional<AbstractAuthenticationFlow<OAuthCredential>> optional = repository.findById(state);

		if (optional.isEmpty())
			return "Authentication timeout";

		AbstractAuthenticationFlow<OAuthCredential> flow = optional.get();
		flow.authenticate(code);
		return "redirect:" + flow.getRedirect().getUrl();
	}
}
