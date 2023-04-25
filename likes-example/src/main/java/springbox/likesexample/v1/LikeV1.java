package springbox.likesexample.v1;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.likesexample.domain.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class LikeV1 {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private DiscussionBoardV1 board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public LikeV1(DiscussionBoardV1 board, User user) {
        this.board = board;
        this.user = user;
    }

    public void addLike(LikeV1 like) {
        user.getLikes().add(like);
    }
}
