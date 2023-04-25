package springbox.likesexample.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

}
