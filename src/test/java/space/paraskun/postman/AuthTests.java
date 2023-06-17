package space.paraskun.postman;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.paraskun.postman.google.auth.AuthenticationService;

@SpringBootTest
public class AuthTests {
    @Autowired
    private AuthenticationService authService;

    @Test
    public void authorize() throws InterruptedException {
        System.out.println(authService.getAuthUrl("state"));

    }
}
