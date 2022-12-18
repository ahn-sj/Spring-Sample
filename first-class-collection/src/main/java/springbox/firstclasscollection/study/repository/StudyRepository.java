package springbox.firstclasscollection.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbox.firstclasscollection.study.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
