package springbox.firstclasscollection.tag;

import lombok.*;
import springbox.firstclasscollection.board.Board;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(uniqueConstraints = @UniqueConstraint(
        name = "tag_name_unique",
        columnNames="name"))
public class Tag {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @Embedded
    private TagName tagName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "board_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tag_to_board"))
    private Board board;

    @Builder
    public Tag(TagName tagName, Board board) {
        this.tagName = tagName;
        this.board = board;
    }

    public static Tag of(String tagName, Board board) {
        return new Tag(TagName.of(tagName), board);
    }

    public Tag(TagName tagName) {
        this.tagName = tagName;
    }

    public static Tag of(String tagName) {

        return new Tag(TagName.of(tagName));
    }

}
