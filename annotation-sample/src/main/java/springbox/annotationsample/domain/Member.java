package springbox.annotationsample.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.annotationsample.domain.enums.Authority;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Member(String name, Authority authority) {
        this.name = name;
        this.authority = authority;
    }

    public void updateName(String name) {
        this.name = name;
    }
}

