package space.paraskun.postman.oauth.google;

import lombok.Getter;
import lombok.Setter;
import space.paraskun.postman.oauth.OAuthCredential;
import space.paraskun.postman.oauth.OAuthException;

@Getter @Setter
public class GoogleOAuth2Credential implements OAuthCredential {
    private final String email;
    private String refreshToken;

    public GoogleOAuth2Credential(String email, String refreshToken) {
        if (email == null || refreshToken == null)
            throw new NullPointerException();

        this.email = email;
        this.refreshToken = refreshToken;
    }

    @Override
    public String getAccessToken() throws OAuthException {
        // TODO Get access token using refresh token
        return null;
    }

    @Override
    public Object getUniqueField() {
        return this.email;
    }
}
