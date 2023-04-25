package springbox.likesexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbox.likesexample.v1.board.DiscussionBoardV1;

public interface DiscussionBoardRepositoryV1 extends JpaRepository<DiscussionBoardV1, Long> {
}
