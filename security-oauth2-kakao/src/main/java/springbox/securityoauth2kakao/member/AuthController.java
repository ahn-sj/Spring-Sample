package springbox.securityoauth2kakao.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/oauth2/{provider}")
    public void loginByOAuth(@PathVariable String provider, @RequestParam String code) {
        log.info("provider = {}", provider);
        log.info("code = {}", code);
        authService.login(provider, code);
    }


}
