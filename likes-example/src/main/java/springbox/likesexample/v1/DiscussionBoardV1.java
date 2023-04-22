package springbox.likesexample.v1.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscussionBoardV1 {

    @Id @GeneratedValue
    private Long id;

    private String title;

    public DiscussionBoardV1(String title) {
        this.title = title;
    }
}
