package springbox.likesexample.like;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbox.likesexample.v1.board.DiscussionBoardV1;
import springbox.likesexample.repository.DiscussionBoardRepository;
import springbox.likesexample.user.User;
import springbox.likesexample.repository.UserRepository;

@SpringBootTest
class LikeServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionBoardRepository discussionBoardRepository;

    @Autowired
    private LikeService likeService;

    @Test
    void likeV1() throws Exception {
        User user = userRepository.save(new User("jae"));
        DiscussionBoardV1 board = discussionBoardRepository.save(new DiscussionBoardV1("title"));

        likeService.likeV1(user.getId(), board.getId());
        likeService.likeV1(user.getId(), board.getId());
    }

    @Test
    void likeV2() throws Exception {
        User user = userRepository.save(new User("jae"));
        DiscussionBoardV1 board = discussionBoardRepository.save(new DiscussionBoardV1("title"));

        likeService.likeV2(user.getId(), board.getId());
    }


}