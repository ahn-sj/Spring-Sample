package springbox.jpaflushtest.blog;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
@Transactional
public class TodoPairTest {

    @Autowired
    EntityManager em;

    @Autowired
    TodoRepository todoRepository;

    private final Todo EMPTY_TODO = new Todo("비어 있다.");

    @Test
    @DisplayName("저장하고 같은지 확인한다.")
    void saveAndEq() {
        Todo todo = todoRepository.save(EMPTY_TODO);
        Assertions.assertThat(todo).isEqualTo(EMPTY_TODO);
    }

    @Test
    @DisplayName("찾아서 같은지 확인한다.")
    void findAndEq() {
        Todo todo = todoRepository.save(EMPTY_TODO);
        Optional<Todo> findTodo = todoRepository.findById(todo.getId());

        Assertions.assertThat(findTodo).hasValue(EMPTY_TODO);
    }
}
