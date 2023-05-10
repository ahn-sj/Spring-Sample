package loadmap.jpapractice.jpql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.username like '%kim%'")
    List<Member> findAllByUsernameIsKim(); // @Param("username") String username
}
