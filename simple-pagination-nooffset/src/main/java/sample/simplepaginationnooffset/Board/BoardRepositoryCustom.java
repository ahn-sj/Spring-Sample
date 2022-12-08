package sample.simplepaginationnooffset.Board;

import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> paginationNoOffset(Long bookId, int pageSize);
}
