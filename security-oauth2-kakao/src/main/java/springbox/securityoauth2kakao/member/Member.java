package springbox.securityoauth2kakao.member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String email;

    private Role role;

    private String name;

}
