package space.paraskun.postman.google.auth;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthorizationHandler {
    private final AuthenticationService googleAuthService;

    @GetMapping("/login/oauth2/code/google")
    public void authorizationCode(@PathParam("code") String code, @PathParam("state") String state) {
        googleAuthService.authorize(code, state);
    }
}
