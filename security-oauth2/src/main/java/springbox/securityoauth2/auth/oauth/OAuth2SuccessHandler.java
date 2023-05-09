package springbox.securityoauth2.auth.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import springbox.securityoauth2.auth.jwt.TokenProvider;
import springbox.securityoauth2.auth.jwt.TokenResponse;
import springbox.securityoauth2.auth.token.RefreshToken;
import springbox.securityoauth2.auth.token.RefreshTokenRepository;
import springbox.securityoauth2.user.User;
import springbox.securityoauth2.user.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

import static springbox.securityoauth2.auth.oauth.CookieAuthorizationRequestRepository.COOKIE_EXPIRE_SECONDS;
import static springbox.securityoauth2.auth.oauth.CookieAuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${oauth.authorizedRedirectUri}")
    private String redirectUri;

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        String targetUrl = determineTargetUrl(response, authentication);
        log.info(">> targetUrl - 2 = {}", targetUrl);

        if (response.isCommitted()) {
            log.debug("Response has already been committed.");
            return;
        }
        clearAuthenticationAttributes(request, response);


        Optional<Cookie> cookie = CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        Cookie c = cookie.get();
        log.info("cookie = {}", c.getName());
        log.info("cookie = {}", c.getComment());
        log.info("cookie = {}", c.getValue());
        log.info("cookie = {}", c.getDomain());
        log.info("cookie = {}", c.getPath());
        log.info("cookie = {}", c.getMaxAge());
        log.info("cookie = {}", c.getSecure());
        log.info("cookie = {}", c.getVersion());

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletResponse response, Authentication authentication) {
        String targetUrl = redirectUri;
        log.info(">> targetUrl - 1 = {}", targetUrl);

        String userEmail = authentication.getName();
        TokenResponse tokenResponse = tokenProvider.generateToken(userEmail);

        /**
         * refresh token 토큰 저장
         */
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        refreshTokenRepository.save(new RefreshToken(tokenResponse.getRefreshToken(), user.getId()));

        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, URLEncoder.encode(tokenResponse.getRefreshToken()), COOKIE_EXPIRE_SECONDS);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokenResponse.getAccessToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
