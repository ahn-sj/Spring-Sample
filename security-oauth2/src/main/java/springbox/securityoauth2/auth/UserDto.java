package springbox.securityoauth2.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
public class UserDto {
    private String name;
    private String email;
    private String picture;


}
