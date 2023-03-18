package springbox.annotationsample.dto;

import lombok.Getter;
import springbox.annotationsample.domain.enums.Authority;

@Getter
public class MemberRequest {
    private String name;
    private Authority authority;
}
