package springbox.likesexample.like;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.likesexample.v1.board.DiscussionBoardV1;
import springbox.likesexample.user.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private DiscussionBoardV1 board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Like(DiscussionBoardV1 board, User user) {
        this.board = board;
        this.user = user;
    }

    public void addLike(Like like) {
        user.getLikes().add(like);
    }
}
