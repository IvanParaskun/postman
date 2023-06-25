package space.paraskun.postman.security.oauth2.google;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import space.paraskun.postman.security.AbstractAuthenticationFlow;
import space.paraskun.postman.security.AbstractAuthenticationHandler;
import space.paraskun.postman.security.session.SessionExpiredException;

@RestController
@RequestMapping
public class GoogleOAuth2AuthenticationHandler extends AbstractAuthenticationHandler<GoogleCredential> {
    protected static final String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/google";

    @Autowired
    public GoogleOAuth2AuthenticationHandler(AbstractAuthenticationFlow<GoogleCredential> flow) {
        super(flow);
    }

    @GetMapping("/login/oauth2/code/google")
    public ModelAndView handleResponse(@PathParam("code") String code, @PathParam("state") String state) {
        try {
            authenticate(state, code);
            return new ModelAndView("redirect:" + getRedirectUrl());
        } catch (SessionExpiredException e) {
            return new ModelAndView("Authentication timeout.");
        }
    }
}
