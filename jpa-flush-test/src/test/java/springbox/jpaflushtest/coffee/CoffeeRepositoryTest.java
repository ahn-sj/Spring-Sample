package springbox.jpaflushtest.coffee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CoffeeRepositoryTest {

    @Autowired
    EntityManager em;

    @Test
    void only_find() throws Exception {
        em.persist(new Coffee("아메리카노"));
        em.persist(new Coffee("카페라떼"));
        em.persist(new Coffee("카푸치노"));

        em.flush();
        em.clear();

        // SELECT 쿼리 1번
        em.find(Coffee.class, 1L);
        em.find(Coffee.class, 1L);
    }

    @Test
    void find_and_jpql() throws Exception {
        em.persist(new Coffee("아메리카노"));
        em.persist(new Coffee("카페라떼"));
        em.persist(new Coffee("카푸치노"));

        em.flush();
        em.clear();

        // SELECT 쿼리 2번
        em.find(Coffee.class, 1L);                            // 영속성 컨텍스트에 존재 X => DB 쿼리
        em.createQuery("select c from Coffee c where c.id = 1") // JPQL SELECT = DB 조회
                .getSingleResult();
    }

    @Test
    @DisplayName("persist -> UPDATE -> find == SELECT 쿼리가 나가면 ")
    void persist_update_find() throws Exception {
        em.persist(new Coffee("아메리카노"));
        em.persist(new Coffee("카페라떼"));
        em.persist(new Coffee("카푸치노"));

        em.createQuery("update Coffee c set c.kind = '에스프레소' where c.id = 1").executeUpdate();

        Coffee findCoffee1 = em.find(Coffee.class, 1L);
        System.out.println("findCoffee1 = " + findCoffee1); // findCoffee1 = Coffee(id=1, kind=아메리카노)
        em.clear();

        Coffee findCoffee2 = em.createQuery("select c from Coffee c where c.id = 1", Coffee.class).getSingleResult();
        System.out.println("findCoffee2 = " + findCoffee2); // findCoffee2 = Coffee(id=1, kind=에스프레소)
    }

}