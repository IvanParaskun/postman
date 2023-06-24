package space.paraskun.postman.security;

import org.springframework.stereotype.Component;
import space.paraskun.postman.account.Account;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class LoggingAuthenticationConsumer implements AuthenticationConsumer {
    private final Logger logger = Logger.getLogger("security");

    @Override
    public void onAuthenticationSuccess(Object state, Account<? extends Credential> account) {
        logger.log(Level.FINE, String.format("Authentication success.\nState: %s\nAccount: %s", state, account));
    }

    @Override
    public void onAuthenticationFailure(Object state, AuthenticationException exception) {
        logger.log(
                Level.WARNING,
                String.format("Authentication failed.\nState: %s\nCause: %s", state, exception.toString())
        );
    }

    @Override
    public String getRedirectUrl() {
        return "https://github.com/IvanParaskun/postman";
    }
}
