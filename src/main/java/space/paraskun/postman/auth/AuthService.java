package space.paraskun.postman.auth;

import org.springframework.data.util.Pair;
import space.paraskun.postman.auth.exception.RefreshTokenExpiredException;

public interface AuthService {
    Pair<String, Long> refreshAccessToken(String refreshToken) throws RefreshTokenExpiredException;
}
