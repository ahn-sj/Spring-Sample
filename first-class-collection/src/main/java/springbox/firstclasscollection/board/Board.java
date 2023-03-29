package springbox.firstclasscollection.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import springbox.firstclasscollection.tag.Tags;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Embedded
    private Tags tags;

    public Board(String title) {
        this.title = title;
    }

    public void addTags(Tags tags) {
        this.tags = tags;
    }
}
