package springbox.securityoauth2.auth.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static springbox.securityoauth2.auth.oauth.CookieAuthorizationRequestRepository.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "login ok";
    }

    @GetMapping("/has-auth")
    public String hasAuth(HttpServletRequest httpServletRequest) {
        Optional<Cookie> cookie = CookieUtils.getCookie(httpServletRequest, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        log.info("cookie = {}", cookie.get());
        return "Yes, You have!";
    }
}
