package space.paraskun.postman.auth;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import space.paraskun.postman.auth.exception.RefreshTokenExpiredException;

@Service
public class GoogleOAuth2Service implements AuthService {
    @Override
    public Pair<String, Long> refreshAccessToken(String refreshToken) throws RefreshTokenExpiredException {
        // TODO Implement
        System.out.println("Refreshed");
        return Pair.of("refreshed", 3600L);
    }
}
