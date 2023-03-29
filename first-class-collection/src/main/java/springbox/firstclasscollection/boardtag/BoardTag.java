package springbox.firstclasscollection.boardtag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springbox.firstclasscollection.BaseEntity;
import springbox.firstclasscollection.board.Board;
import springbox.firstclasscollection.tag.Tag;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTag extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(name = "fk_boardtag_to_board"))
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_boardtag_to_tag"))
    private Tag tag;

    public BoardTag(Board board, Tag tag) {
        this.board = board;
        this.tag = tag;
    }

    public static BoardTag of(Board board, Tag tag) {
        return new BoardTag(board, tag);
    }
}
