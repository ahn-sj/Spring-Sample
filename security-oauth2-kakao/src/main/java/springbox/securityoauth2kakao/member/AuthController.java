package springbox.securityoauth2kakao.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springbox.securityoauth2kakao.security.LoginResponse;
import springbox.securityoauth2kakao.security.token.RefreshToken;
import springbox.securityoauth2kakao.security.token.RefreshTokenRepository;
import springbox.securityoauth2kakao.security.token.TokenProvider;
import springbox.securityoauth2kakao.security.token.TokenResponse;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    @GetMapping("/oauth2/{provider}")
    public LoginResponse loginByOAuth(@PathVariable String provider, @RequestParam String code) {
        log.info("provider = {}", provider);
        log.info("code = {}", code);
        LoginResponse loginResponse = authService.login(provider, code);

        return loginResponse;
    }

    @PostMapping("/join")
    public String join(@RequestBody Map<String, String> user) {
        log.info(">>>>> 회원가입 시도");

        Member member = Member.builder()
                .email(user.get("email"))
                .name("aaa")
                .password("aaapwd")
                .picture("url blabla")
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        log.info(">>>>> 회원가입 완료");

        return user.toString();
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody Map<String, String> user) {
        log.info(">>>>> 로그인 시도");
        String key = "email";
        String value = user.get(key);
        log.info("user {} = {}", key, value);

        Member findmember = memberRepository.findByEmail(value)
                .orElseThrow(() -> new IllegalArgumentException("Not Joined User"));
        log.info("findmember => {}", findmember);

        TokenResponse tokenResponse = tokenProvider.generateToken(findmember.getEmail());
        log.info("tokenResponse => {}", tokenResponse);

        refreshTokenRepository.save(new RefreshToken(tokenResponse.getRefreshToken(), findmember.getId()));

        log.info(">>>>> refresh token 저장 완료 token   = [{}]", tokenResponse.getRefreshToken());
        log.info(">>>>> refresh token 저장 완료 user id = [{}]", findmember.getId());
        log.info(">>>>> 로그인 완료");
        return tokenResponse;
    }

    @PostMapping("/auth")
    public String auth() {
        return "Yes, You have authorization!";
    }

}
