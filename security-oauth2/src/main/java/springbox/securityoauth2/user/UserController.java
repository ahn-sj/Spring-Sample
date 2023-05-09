package springbox.securityoauth2.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springbox.securityoauth2.auth.jwt.TokenProvider;
import springbox.securityoauth2.auth.jwt.TokenResponse;
import springbox.securityoauth2.auth.token.RefreshToken;
import springbox.securityoauth2.auth.token.RefreshTokenRedisRepository;

import java.util.Map;

@Slf4j
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRedisRepository refreshTokenRepository;

    public UserController(
            UserRepository userRepository,
            TokenProvider tokenProvider,
            RefreshTokenRedisRepository refreshTokenRepository
    ) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/join")
    public String join() {
        log.info(">>>>> 회원가입 시도");

        User user = User.builder()
                .id(Long.valueOf(1))
                .email("aaa@gmail.com")
                .name("aaa")
                .password("aaapwd")
                .picture("url blabla")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        log.info(">>>>> 회원가입 완료");

        return user.toString();
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody Map<String, String> user) {
        log.info(">>>>> 로그인 시도");
        String key = "email";
        String value = user.get(key);
        log.info("user {} = {}", key, value);

        User findUser = userRepository.findByEmail(value)
                .orElseThrow(() -> new IllegalArgumentException("Not Joined User"));
        log.info("findUser => {}", findUser);

        TokenResponse tokenResponse = tokenProvider.generateToken(findUser.getEmail());
        log.info("tokenResponse => {}", tokenResponse);

        refreshTokenRepository.save(new RefreshToken(tokenResponse.getRefreshToken(), findUser.getId()));

        log.info(">>>>> refresh token 저장 완료 token   = [{}]", tokenResponse.getRefreshToken());
        log.info(">>>>> refresh token 저장 완료 user id = [{}]", findUser.getId());
        log.info(">>>>> 로그인 완료");
        return tokenResponse;
    }

    /**
     * 1. refreshToken으로 access token과 refresh token 재발급
     * 2. bearer 를 제외한 나머지 부분으로 저장
     */
//    @PostMapping("/refresh-token")
//    public void getRefreshToken(@RequestBody Map<String, String> refreshToken) {
//        refreshTokenRepository.findById(refreshToken.get("refresh"));
//    }


}
