package space.paraskun.postman.oauth.google;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import space.paraskun.postman.security.AuthenticationService;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.oauth.OAuthCredential;
import space.paraskun.postman.security.SessionExpiredException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GoogleOAuthAuthenticationHandler {
	public static String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/google";
	private final AuthenticationService<OAuthCredential> authenticationService;

	@GetMapping("/login/oauth2/code/google")
	public ModelAndView onResponse(@PathParam("code") String code, @PathParam("state") String state) {
		try {
			AbstractAuthenticationFlow<OAuthCredential> flow = authenticationService.restore(state);
			flow.authenticate(code);
			return new ModelAndView("redirect:" + flow.getAuthenticationConsumer().getRedirectUrl());
		} catch (SessionExpiredException e) {
			return new ModelAndView("redirect:https://google.com");
		}
	}
}
