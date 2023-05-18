package loadmap.jpapractice.jpql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.List;

@SpringBootTest
public class JpaMain {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @Transactional
    void jpql_path_expression() throws Exception {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member("member1");
            em.persist(member1);

            Member member2 = new Member("member2");
            em.persist(member2);

            em.flush();
            em.close();

            String query = "select t.members from Team t";
            Collection members = em.createQuery(query, Collection.class).getResultList();

            for (Object member : members) {
                System.out.println("member = " + member);
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Test
    void fetch_join_single_entity() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team("TEAM A");
            Team teamB = new Team("TEAM B");
            em.persist(teamA);
            em.persist(teamB);

            Member member1 = new Member("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team";
            List<Member> members = em.createQuery(query, Member.class).getResultList();

            for (Member member : members) {
                System.out.println("member.username = " + member.getUsername() +
                        ", member.team.name = " + member.getTeam().getName());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Test
    void fetch_join_collection_entity() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team("TEAM A");
            Team teamB = new Team("TEAM B");
            em.persist(teamA);
            em.persist(teamB);

            Member member1 = new Member("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member("member2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member("member3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select distinct t from Team t join fetch t.members";
            List<Team> teams = em.createQuery(query, Team.class).getResultList();
            System.out.println("teams.size() = " + teams.size());

            for (Team team : teams) {
                System.out.println("team.getName() = " + team.getName() +
                        ", team.getMembers.size = " +  team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

