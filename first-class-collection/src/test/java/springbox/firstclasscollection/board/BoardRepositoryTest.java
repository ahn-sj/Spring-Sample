package springbox.firstclasscollection.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springbox.firstclasscollection.boardtag.BoardTag;
import springbox.firstclasscollection.tag.Tag;
import springbox.firstclasscollection.tag.TagRepository;
import springbox.firstclasscollection.tag.Tags;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Board에 Tags를 저장한다.")
    void saveBoardWithTags() throws Exception {
        Tags tags = Tags.of(Arrays.asList(
                Tag.of("태그를"), Tag.of("만든다"), Tag.of("두개를")));

        Board board = Board.builder()
                .title("스터디 모집")
                .tags(tags)
                .build();

        Board saveBoard = boardRepository.save(board);

        List<BoardTag> findBoardTags = saveBoard.getBoardTags();
        List<Tag> findTags = tagRepository.findAll();
        List<Board> findBoards = boardRepository.findAll();

        assertThat(findBoardTags).hasSize(3);
        assertThat(findBoards).hasSize(1);
        assertThat(findTags).hasSize(3);
    }
}
