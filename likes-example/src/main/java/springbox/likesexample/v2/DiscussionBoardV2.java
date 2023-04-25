package springbox.likesexample.v2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscussionBoardV2 {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private int likeStatus;

    public DiscussionBoardV2(String title) {
        this.title = title;
    }

    public void addLike() {
        likeStatus += 1;
    }
}
