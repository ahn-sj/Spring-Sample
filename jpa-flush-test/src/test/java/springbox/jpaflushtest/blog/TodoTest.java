package springbox.jpaflushtest.blog;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoTest {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    EntityManager em;

    private final Todo emptyTodo = new Todo("비어 있다.");

    @Test
    @DisplayName("영속 상태로 만든 엔티티를 엔티티 매니저로 조회한다.")
    void 엔티티_매니저로_조회() {
        Todo todo1 = new Todo("할일-1");
        Todo todo2 = new Todo("할일-2");
        Todo todo3 = new Todo("할일-3");

        em.persist(todo1);
        em.persist(todo2);
        em.persist(todo3);

        Todo findTodo = em.find(Todo.class, todo1.getId());
        System.out.println("[Spring Jpa] => findTodo = " + findTodo);
    }

    @Test
    @DisplayName("Spring Data JPA도 마찬가지로 flush가 나가지 않는다.")
    void DATA_JPA도_flush가_나가지_않는다() {
        Todo todo1 = new Todo("할일-1");
        Todo todo2 = new Todo("할일-2");
        Todo todo3 = new Todo("할일-3");

        Todo saveTodo1 = todoRepository.save(todo1);
        Todo saveTodo2 = todoRepository.save(todo2);
        Todo saveTodo3 = todoRepository.save(todo3);

        Todo findTodo = em.find(Todo.class, saveTodo1.getId());
        System.out.println("[Spring Data JPA] => findTodo = " + findTodo);
    }

    @Test
    @DisplayName("영속 상태로 만든 엔티티를 JQPL로 조회한다.")
    void JPQL로_조회() {
        Todo todo1 = new Todo("할일-1");
        Todo todo2 = new Todo("할일-2");
        Todo todo3 = new Todo("할일-3");

        em.persist(todo1);
        em.persist(todo2);
        em.persist(todo3);

        System.out.println("====> is call before JPQL");

        Todo findTodo = em.createQuery("select t from Todo t where id = :id", Todo.class)
                .setParameter("id", todo1.getId()).getSingleResult();
        System.out.println("[JPQL] => findTodo = " + findTodo);
    }

    @Test
    @DisplayName("JPQL select")
    void jpql_select_example() {
        Todo todo = new Todo("할일-1");

        em.persist(todo);

        em.flush();
        em.clear();

        Todo findTodo = em.createQuery("select t from Todo t where id = :id", Todo.class)
                .setParameter("id", todo.getId()).getSingleResult();
        System.out.println("======================");
        Todo findTodo1 = em.find(Todo.class, findTodo.getId()); // 여기서 쿼리가 한번 더 나가는지?
    }

    @Test
    @DisplayName("JPQL update select")
    void updateAfterSelect() {
        Todo todo = new Todo("할일-1");
        em.persist(todo);
        em.flush();
        em.clear();

        em.createQuery("update Todo t set t.content = '전부 끝냄' where t.id = :id")
                .setParameter("id", todo.getId())
                .executeUpdate();

        System.out.println("===============");

        em.find(Todo.class, todo.getId());
    }

    @Test
    @DisplayName("findByContent")
    void 투두를_여러번_조회() {
        // given
        Todo todo1 = new Todo("할일-1");
        Todo todo2 = new Todo("할일-2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        em.flush();
        em.clear();

        Todo findTodo = todoRepository.findByContent(todo1.getContent()).get();
        Todo findfindTodo = todoRepository.findByContent(todo1.getContent()).get();
    }

    @Test
    @DisplayName("1) 엔티티 save(persist) -> 2) JPQL update -> 3) JPA findOne -> 4) JPQL findOne")
    void 복잡한_조회와_변경_문제O() {
        // 1) 3개의 엔티티 persist
        Todo todo = new Todo("할일");
        Todo saveTodo = todoRepository.save(todo);

        // 2) JPQL Update
        em.createQuery("update Todo t set t.content = '전부 끝냄' where t.id = :id")
                .setParameter("id", saveTodo.getId())
                .executeUpdate();

        // 3) JPA findOne
        Todo findTodo1 = em.find(Todo.class, saveTodo.getId());

        // 4) JPQL findOne
        Todo findTodo2 = em.createQuery("select t from Todo t where t.id = :id", Todo.class)
                .setParameter("id", saveTodo.getId())
                .getSingleResult();

        System.out.println("findTodo1 = " + findTodo1);
        System.out.println("findTodo2 = " + findTodo2);

        // error 발생
        assertThat(findTodo2.getContent()).isEqualTo("전부 끝냄");
    }

    @Test
    @DisplayName("1) 엔티티 save(persist) -> 2) JPQL update -> 3) JPA findOne -> 4) JPQL findOne")
    void 복잡한_조회와_변경_문제X() {
        // 1) 3개의 엔티티 persist
        Todo todo = new Todo("할일");
        Todo saveTodo = todoRepository.save(todo);

        // 2) JPQL Update
        em.createQuery("update Todo t set t.content = '전부 끝냄' where t.id = :id")
                .setParameter("id", saveTodo.getId())
                .executeUpdate();

        em.clear();

        // 3) JPA findOne
        Todo findTodo1 = em.find(Todo.class, saveTodo.getId());

        // em.setFlushMode(FlushModeType.COMMIT);

        // 4) JPQL findOne
        Todo findTodo2 = em.createQuery("select t from Todo t where t.id = :id", Todo.class)
                .setParameter("id", saveTodo.getId())
                .getSingleResult();

        System.out.println("findTodo1 = " + findTodo1);
        System.out.println("findTodo2 = " + findTodo2);

        assertThat(findTodo1.getContent()).isEqualTo("전부 끝냄");
        assertThat(findTodo2.getContent()).isEqualTo("전부 끝냄");
    }
}
