package springbox.likesexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbox.likesexample.v1.LikeV1;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeV1, Long> {
    Optional<LikeV1> findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

}
