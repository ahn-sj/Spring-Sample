package springbox.jpan1problem.jojoldu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcademyRepository extends JpaRepository<Academy, Long> {
    /**
     * 1. join fetch를 통한 조회
     */
    @Query("select a from Academy a join fetch a.subjects")
    List<Academy> findAllJoinFetch();
}
