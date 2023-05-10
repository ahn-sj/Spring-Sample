package springbox.securityoauth2kakao.member;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    private String password;

    private String picture;

    @Builder
    public Member(String email, Role role, String name, String password, String picture) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.password = password;
        this.picture = picture;
    }

    public void update(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.picture = member.getPicture();
    }
}
