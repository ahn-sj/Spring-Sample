package springbox.jpan1problem.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.jpan1problem.article.Article;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
