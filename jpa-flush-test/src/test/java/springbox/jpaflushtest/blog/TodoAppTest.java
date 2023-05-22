package springbox.jpaflushtest.blog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class TodoAppTest {

    @Autowired
    EntityManager em;

    @Autowired
    TodoRepository todoRepository;

    JpaEntityInformation<Todo, ?> entityInformation;

    @BeforeEach
    void init() {
        entityInformation = JpaEntityInformationSupport.getEntityInformation(Todo.class, em);
    }

    @Test
    void service() {
        Todo input = new Todo("오늘 할 일");
        System.out.println("[Controller -> Service] -> 비영속 상태인가? = " + entityInformation.isNew(input));

        Todo saveTodo = todoRepository.save(input);
        System.out.println("[Repository.save]       -> 비영속 상태인가? = " + entityInformation.isNew(saveTodo));
    }
}
