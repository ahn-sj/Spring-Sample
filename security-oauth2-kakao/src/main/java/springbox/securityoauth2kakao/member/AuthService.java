package springbox.securityoauth2kakao.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import springbox.securityoauth2kakao.security.token.OauthTokenResponse;
import springbox.securityoauth2kakao.security.token.TokenProvider;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public void login(String providerName, String code) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
        OauthTokenResponse token = getToken(provider, code);

    }

    private OauthTokenResponse getToken(ClientRegistration provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
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
}
