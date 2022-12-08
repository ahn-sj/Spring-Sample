package sample.simplepaginationnooffset.Board;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 105; i++) {
            new ResponseDto(boardRepository.save(new Board("제목" + i)));
        }
    }

    @GetMapping("/api/v1/home")
    public List<ResponseDto> apiSimpleBoards() {
        return boardRepository.findAll().stream()
                .map(board -> new ResponseDto(board))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v2/home")
    public List<ResponseDto> apiBoards(@RequestParam(name = "start", required = false) Long start,
                                       @PageableDefault(size = 3) Pageable pageable) {
        return boardRepository.paginationNoOffset(start, pageable.getPageSize()).stream()
                .map(board -> new ResponseDto(board))
                .collect(Collectors.toList());
    }

    @Getter
    static class ResponseDto {
        private String title;

        public ResponseDto(Board board) {
            this.title = board.getTitle();
        }
    }
}
