package springbox.annotationsample.dto;

import lombok.Getter;

@Getter
public class MemberResponse {
    private String name;
    private String authority;

    public MemberResponse(String name, String authority) {
        this.authority = authority;
        this.name = name;
    }
}
