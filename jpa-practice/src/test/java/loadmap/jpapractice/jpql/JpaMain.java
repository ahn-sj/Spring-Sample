package loadmap.jpapractice.jpql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

@SpringBootTest
public class JpaMain {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void jpql_path_expression() throws Exception {
        Member member1 = new Member("member1", 20);
        em.persist(member1);

        Member member2 = new Member("member2", 20);
        em.persist(member2);

        em.flush();
        em.close();

        String query = "select t.members from Team t";
        Collection members = em.createQuery(query, Collection.class).getResultList();

        for (Object member : members) {
            System.out.println("member = " + member);
        }
    }


}
