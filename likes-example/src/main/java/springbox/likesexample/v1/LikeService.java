package springbox.likesexample.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbox.likesexample.v2.DiscussionBoardRepositoryV2;
import springbox.likesexample.repository.LikeRepository;
import springbox.likesexample.domain.User;
import springbox.likesexample.repository.UserRepository;
import springbox.likesexample.v2.DiscussionBoardV2;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final DiscussionBoardRepositoryV1 discussionBoardRepositoryV1;
    private final DiscussionBoardRepositoryV2 discussionBoardRepositoryV2;

    /**
     * 하나의 API에서 LIKE/UN_LIKE 처리
     * @param userId
     * @param boardId
     * @return
     */
    public void likeV1(Long userId, Long boardId) {
        User user = findUser(userId);
        DiscussionBoardV1 board = findBoardV1(boardId);

        boolean isExist = existLike(userId, boardId);

        if(isExist) {
            LikeV1 like = likeRepository.save(new LikeV1(board, user));
            like.addLike(like);
        }
        if(!isExist) {
            likeRepository.deleteByUserIdAndBoardId(userId, boardId);
        }
    }

    /**
     * LIKE와 UNLIKE를 분리하고 게시물에 좋아요 개수를 추가
     * @param userId
     * @param boardId
     * @return
     */
    public void likeV2(Long userId, Long boardId) {
        User user = findUser(userId);
        DiscussionBoardV2 board = findBoardV2(boardId);

        if(existLike(userId, boardId)) {
            // LikeV1 like = likeRepository.save(new LikeV1(board, user));
            //like.addLike(like);
            //board.
        }
    }

    private DiscussionBoardV1 findBoardV1(Long boardId) {
        return discussionBoardRepositoryV1.findById(boardId).orElseThrow(() -> {
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");
        });
    }

    private DiscussionBoardV2 findBoardV2(Long boardId) {
        return discussionBoardRepositoryV2.findById(boardId).orElseThrow(() -> {
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");
        });
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new RuntimeException("해당 멤버가 존재하지 않습니다.");
        });
    }

    private boolean existLike(Long userId, Long boardId) {
        return likeRepository.findByUserIdAndBoardId(userId, boardId).isEmpty();
    }
}
