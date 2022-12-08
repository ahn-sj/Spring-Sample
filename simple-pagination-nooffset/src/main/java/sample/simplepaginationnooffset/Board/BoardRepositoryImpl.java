package sample.simplepaginationnooffset.Board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static sample.simplepaginationnooffset.Board.QBoard.board;

public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Board> paginationNoOffset(Long boardId, int pageSize) {
        return queryFactory
                .selectFrom(board)
                .where(ltBoardId(boardId))
                .limit(pageSize + 1)
                .fetch();
    }

    private BooleanExpression ltBoardId(Long boardId) {
        if (boardId == null) {
            return null;
        }
        return board.id.gt(boardId);
    }
}
