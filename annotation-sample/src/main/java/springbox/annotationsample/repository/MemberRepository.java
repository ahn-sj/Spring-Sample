package springbox.annotationsample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbox.annotationsample.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
