package springbox.annotationsample.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import springbox.annotationsample.exception.NoSuchAuthorityException;

public enum Authority {
    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자");

    private String desc;

    Authority(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static Authority from(String val) {
        String upperVal = val.toUpperCase();
        try {
            return Authority.valueOf(upperVal);
        } catch (IllegalArgumentException e) {
            throw new NoSuchAuthorityException("해당 권한은 존재하지 않습니다. [" + upperVal + "]");
        }
    }
}
