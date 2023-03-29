package springbox.firstclasscollection.tag;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import springbox.firstclasscollection.board.Board;
import springbox.firstclasscollection.board.BoardRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("Tag를 저장한다.")
    void saveTag() throws Exception {
        Tag tag1 = Tag.of("스터디");
        Tag saveTag1 = tagRepository.save(tag1);

        assertThat(saveTag1.getTagName().getValue()).isEqualTo("스터디");
    }

    @Test
    @DisplayName("게시글에 여러 개의 태그를 저장한다.")
    @Commit
    @Rollback(value = false)
    void saveTagsAndBoard() throws Exception {
        Board board = new Board("제목");

        Tag tag1 = Tag.of("스터디");
        Tag tag2 = Tag.of("모집");
        board.addTags(Tags.of(Arrays.asList(tag1, tag2)));

        Board saveBoard = boardRepository.save(board);

        Assertions.assertThat(saveBoard.getTags().size()).isEqualTo(2);
    }


}
