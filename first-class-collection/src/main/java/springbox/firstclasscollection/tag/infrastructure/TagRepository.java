package springbox.firstclasscollection.tag.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import springbox.firstclasscollection.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
