package springbox.jpan1problem.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbox.jpan1problem.article.Article;
import springbox.jpan1problem.article.ArticleRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    public void init() {
        User user = userRepository.save(new User("이름"));
        Article article = new Article("제목", "내용");
        article.setUser(user);
        articleRepository.save(article);
    }

    @Test
    @DisplayName("Lazy type은 User 검색 후 필드 검색 시 N+1 발생")
    public void lazy_user_find() throws Exception {
        // given
        List<User> users = userRepository.findAll();

        for (User user : users) {
            System.out.println("user = " + user.getArticles().size());
        }
    }
}