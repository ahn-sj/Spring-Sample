package springbox.jpaflushtest.blog;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString @Getter
public class Todo {

    @Id @GeneratedValue
    private Long id;

    private String content;

    public Todo(String content) {
        this.content = content;
    }
}
