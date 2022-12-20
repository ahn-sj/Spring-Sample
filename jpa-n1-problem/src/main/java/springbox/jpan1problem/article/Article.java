package springbox.jpan1problem.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.jpan1problem.user.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Article {

    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getArticles().add(this);
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
