package springbox.securityoauth2kakao.security.token;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}