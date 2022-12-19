package springbox.firstclasscollection.study.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import springbox.firstclasscollection.study.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
