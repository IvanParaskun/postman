package space.paraskun.postman.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import space.paraskun.postman.account.Account;

@Component
@RequiredArgsConstructor
public class ConsoleAuthenticationConsumer implements AuthenticationConsumer {
    @Override
    public void onAuthenticationSuccess(Object state, Account<? extends Credential> account) {
        System.out.printf("Authentication success:\n\tState: %s\n\tAccount: %s\n\n", state, account);
    }

    @Override
    public void onAuthenticationFailure(Object state, AuthenticationException exception) {
        System.out.printf("Authentication failed:\n\tState: %s\n\tCause: %s\n\n", state, exception.toString());
    }

    @Override
    public String getRedirectUrl() {
        return "https://github.com/IvanParaskun/postman";
    }
}
