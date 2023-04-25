package springbox.likesexample.like;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbox.likesexample.v2.DiscussionBoardRepositoryV2;
import springbox.likesexample.v1.DiscussionBoardV1;
import springbox.likesexample.v1.DiscussionBoardRepositoryV1;
import springbox.likesexample.domain.User;
import springbox.likesexample.repository.UserRepository;
import springbox.likesexample.v1.LikeService;
import springbox.likesexample.v2.DiscussionBoardV2;

@SpringBootTest
class LikeV1ServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionBoardRepositoryV1 discussionBoardRepositoryV1;

    @Autowired
    private DiscussionBoardRepositoryV2 discussionBoardRepositoryV2;

    @Autowired
    private LikeService likeService;

    @Test
    void likeV1() throws Exception {
        User user = userRepository.save(new User("jae"));
        DiscussionBoardV1 board = discussionBoardRepositoryV1.save(new DiscussionBoardV1("title"));

        likeService.likeV1(user.getId(), board.getId());
        likeService.likeV1(user.getId(), board.getId());
    }

    @Test
    void likeV2() throws Exception {
        User user = userRepository.save(new User("jae"));
        DiscussionBoardV2 board = discussionBoardRepositoryV2.save(new DiscussionBoardV2("title"));

        likeService.likeV2(user.getId(), board.getId());
    }


}