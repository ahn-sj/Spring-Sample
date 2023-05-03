package springbox.securityoauth2.auth.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import springbox.securityoauth2.auth.jwt.Token;
import springbox.securityoauth2.auth.jwt.TokenProvider;
import springbox.securityoauth2.auth.jwt.TokenResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        log.info("OAuth2User in Principal = {}", oAuth2User);

        log.info("토큰 발행 시작");

        TokenResponse tokenResponse = tokenProvider.generateToken(oAuth2User.getName());
        log.info("tokenResponse = {}", tokenResponse);
        String targetUrl = UriComponentsBuilder
                .fromUriString("/home")
                .queryParam("token", "token")
                .build()
                .toUriString();
        log.info("targetUrl = {}", targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
