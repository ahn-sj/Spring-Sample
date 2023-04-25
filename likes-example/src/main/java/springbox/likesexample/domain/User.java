package springbox.likesexample.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.likesexample.like.Like;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }
}
