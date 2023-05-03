package springbox.securityoauth2.auth.oauth;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String picture;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes,
                            String attributeKey, String name,
                            String email, String picture) {
        this.attributes = attributes;
        this.attributeKey= attributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuth2Attributes of(String provider, String attributeKey, Map<String, Object> attributes) {
        switch (provider) {
            case "GOOGLE":
                return ofGoogle(attributeKey, attributes);
            case "KAKAO":
                return ofKakao("email", attributes);
            default:
                throw new RuntimeException("Invalid Provider Type");
        }
    }

    private static OAuth2Attributes ofGoogle(String attributeKey,
                                             Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attributes ofKakao(String attributeKey,
                                            Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String)kakaoProfile.get("profile_image_url"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> memberAttribute = new HashMap<>();
        memberAttribute.put("id", attributeKey);
        memberAttribute.put("key", attributeKey);
        memberAttribute.put("name", name);
        memberAttribute.put("email", email);
        memberAttribute.put("picture", picture);

        return memberAttribute;
    }
}
