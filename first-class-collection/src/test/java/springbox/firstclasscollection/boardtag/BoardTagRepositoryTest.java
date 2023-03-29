package springbox.firstclasscollection.boardtag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import springbox.firstclasscollection.board.Board;
import springbox.firstclasscollection.tag.Tag;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardTagRepositoryTest {

    @Test
    @DisplayName("BoardTag 를 저장한다.")
    void saveBoardTag() throws Exception {

        Board board = Board.builder().title("제목").build();
        Tag tag = Tag.of("태그");

        BoardTag boardTag = BoardTag.of(board, tag);

        assertThat(boardTag.getBoard()).isEqualTo(boardTag);
        assertThat(boardTag.getTag()).isEqualTo(tag);
    }


}
