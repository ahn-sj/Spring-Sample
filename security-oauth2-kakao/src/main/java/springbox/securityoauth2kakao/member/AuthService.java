package springbox.securityoauth2kakao.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import springbox.securityoauth2kakao.security.LoginResponse;
import springbox.securityoauth2kakao.security.oauth.OAuth2Attributes;
import springbox.securityoauth2kakao.security.token.*;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public LoginResponse login(String providerName, String code) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
        OAuthTokenResponse oAuthTokenResponse = getToken(provider, code);

        Map<String, Object> userAttributes = getUserAttributes(provider, oAuthTokenResponse);
        OAuth2Attributes attributes = getMember(providerName, userAttributes);

        Member saveOrUpdateMember = saveOrUpdate(attributes);

        TokenResponse tokenResponse = tokenProvider.generateToken(saveOrUpdateMember.getEmail());
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        refreshTokenRepository.save(new RefreshToken(refreshToken, saveOrUpdateMember.getId()));

        log.info("access token = {}", accessToken);
        log.info("refresh token = {}", refreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member saveOrUpdate(OAuth2Attributes attributes) {
        log.info(">> email = {}", attributes.getEmail());
        Member member = memberRepository.findByEmail(attributes.getEmail()).orElseGet(() -> attributes.toEntity());
        member.update(member);
        return memberRepository.save(member);
    }

    private OAuth2Attributes getMember(String provider, Map<String, Object> userAttributes) {
        return OAuth2Attributes.of(provider, "email", userAttributes);
    }

    private OAuthTokenResponse getToken(ClientRegistration provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .block();
    }

    private Object tokenRequest(String code, ClientRegistration provider) {
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("client_id", provider.getClientId());
        return formData;
    }

    private Map<String, Object> getUserAttributes(ClientRegistration provider, OAuthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}
