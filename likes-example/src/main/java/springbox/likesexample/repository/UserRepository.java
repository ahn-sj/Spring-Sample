package springbox.likesexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbox.likesexample.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
