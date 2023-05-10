package loadmap.jpapractice.cascade;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ParentTest {

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    ChildRepository childRepository;

    @Autowired
    EntityManager em;

    @Test
    void cascade() throws Exception {
        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();

        parent.addChild(child1);
        parent.addChild(child2);

        parentRepository.save(parent);
    }

    @Test
    @DisplayName("orphanRemoval 설정이 켜진 상태로 부모 객체를 삭제하면 관련 자식 객체들도 모두 지워진다.")
    @Transactional
    @Commit
    void orphanRemoval_remove_parent() throws Exception {
        Child child1 = new Child();
        Child child2 = new Child();
        Parent parent = new Parent();

        parent.addChild(child1);
        parent.addChild(child2);

        childRepository.save(child1);
        childRepository.save(child2);
        parentRepository.save(parent);

        em.flush(); // insert
        em.clear(); // clear

        Parent findParent = parentRepository.findById(parent.getId()).get();
        assertThat(findParent.getChilds().size()).isEqualTo(2);
        parentRepository.deleteById(parent.getId());

        List<Parent> parents = parentRepository.findAll();
        List<Child> childs = childRepository.findAll();

        assertThat(parents.size()).isEqualTo(0);
        assertThat(childs.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("cascade와 orphanRemoval을 함께 실행")
    @Transactional
    @Commit
    void orphanRemoval_with_cascade() throws Exception {
        Child child1 = new Child();
        Child child2 = new Child();
        Parent parent = new Parent();

        parent.addChild(child1);
        parent.addChild(child2);

        parentRepository.save(parent);

        em.flush(); // insert
        em.clear(); // clear

        Parent findParent = parentRepository.findById(parent.getId()).get();
        assertThat(parent.getChilds().size()).isEqualTo(2);

        findParent.addChild(new Child());
        em.flush();
        em.clear();

        Parent insertAndFindParent = parentRepository.findById(parent.getId()).get();
        assertThat(insertAndFindParent.getChilds().size()).isEqualTo(3);
    }
}
