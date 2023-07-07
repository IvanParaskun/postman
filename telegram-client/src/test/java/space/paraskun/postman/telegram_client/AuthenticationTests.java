package space.paraskun.postman.telegram_client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import space.paraskun.postman.security.AuthenticationService;
import space.paraskun.postman.security.oauth2.google.GoogleCredential;

@SpringBootTest
public class AuthenticationTests {
    private AuthenticationService<GoogleCredential> googleAuthenticationService;


}
