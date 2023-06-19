package space.paraskun.postman.google.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.paraskun.postman.security.AuthenticationService;

@RestController
@RequestMapping
public class OAuthResponseHandler {
	public static final String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/google";
	private final AuthenticationService authenticationService;

	public OAuthResponseHandler(@Autowired AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@GetMapping("/login/oauth2/code/google")
	public void handleResponseCode(@RequestParam("code") String code, @RequestParam("state") String state) {
		authenticationService.authenticate(code, state);
	}
}
