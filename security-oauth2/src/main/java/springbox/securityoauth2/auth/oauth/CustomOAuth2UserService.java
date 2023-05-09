package springbox.securityoauth2.auth.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import springbox.securityoauth2.user.User;
import springbox.securityoauth2.user.UserRepository;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        log.info("oAuth2UserRequest.getClientRegistration() = {}", oAuth2UserRequest.getClientRegistration());

        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        /**
         * 여기서 유저 객체 생성을 함
         */
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2Attributes.getEmail())) {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }

        User user = saveOrUpdate(oAuth2Attributes);
        log.info(">>>>>>> Map = {}, Email = {}", oAuth2Attributes.getAttributes(), oAuth2Attributes.getEmail());

        Map<String, Object> memberAttribute = oAuth2Attributes.convertToMap();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())), // 여기서 OAuth2로 로그인한 계정의 권한을 선정한다.
                memberAttribute,
                "email");

    }

    private User saveOrUpdate(OAuth2Attributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()).orElse(attributes.toEntity());
        user.update(attributes.getName(), attributes.getPicture());
        return userRepository.save(user);
    }

}
