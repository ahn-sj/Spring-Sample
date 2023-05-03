package springbox.securityoauth2.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springbox.securityoauth2.auth.jwt.TokenProvider;
import springbox.securityoauth2.auth.jwt.TokenResponse;

import java.util.Map;

@Slf4j
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserController(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/join")
    public String join() {
        log.info(">>>>> 로그인 시도");

        User user = User.builder()
                .id(Long.valueOf(1))
                .email("aaa@gmail.com")
                .name("aaa")
                .picture("url blabla")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        log.info(">>>>> 로그인 완료");

        return user.toString();
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody Map<String, String> user) {
        String key = "email";
        String value = user.get(key);
        log.info("user {} = {}", key, value);

        User findUser = userRepository.findByEmail(value)
                .orElseThrow(() -> new IllegalArgumentException("Not Joined User"));

        return tokenProvider.generateToken(findUser.getEmail());
    }

}
