package springbox.securityoauth2kakao.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "비회원"),
    USER("ROLE_USER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자");

    private String code;
    private String name;
}
