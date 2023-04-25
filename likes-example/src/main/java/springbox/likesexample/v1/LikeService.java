package springbox.likesexample.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbox.likesexample.repository.LikeRepository;
import springbox.likesexample.v1.board.DiscussionBoardV1;
import springbox.likesexample.repository.DiscussionBoardRepository;
import springbox.likesexample.user.User;
import springbox.likesexample.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final DiscussionBoardRepository discussionBoardRepository;

    /**
     * 하나의 API에서 LIKE/UN_LIKE 처리
     * @param userId
     * @param boardId
     * @return
     */
    public void likeV1(Long userId, Long boardId) {
        User user = findUser(userId);
        DiscussionBoardV1 board = findBoard(boardId);

        boolean isExist = existLike(userId, boardId);

        if(isExist) {
            Like like = likeRepository.save(new Like(board, user));
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
        DiscussionBoardV1 board = findBoard(boardId);

        if(existLike(userId, boardId)) {
            Like like = likeRepository.save(new Like(board, user));
            like.addLike(like);
        }
    }

    private DiscussionBoardV1 findBoard(Long boardId) {
        return discussionBoardRepository.findById(boardId).orElseThrow(() -> {
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
