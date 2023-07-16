package board.comment.search.repository.search;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import board.comment.search.domain.Board;
import board.comment.search.domain.QBoard;
import board.comment.search.domain.QReply;
import board.comment.search.dto.board.BoardListRcntDTO;
import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSerach {

    // Boadr Entity Super 상속 
    public BoardSearchImpl() {
        super(Board.class);
    }

    // Board List SearchImpl
    @Override
    public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO pageRequestDTO) {
        log.info("Board List Is Running");
        // Default makePageable Using 
        Pageable pageable = makePageable(pageRequestDTO);
        // Querydsl Entity Using 
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        // BoardTable Query Create
        JPQLQuery<Board> query = from(board);
        // Left Outer Join 
        query.leftJoin(reply).on(reply.board.eq(board));
        // PageRequestDTO SearchType & keyword Get 
        String keyword = pageRequestDTO.getKeyword();
        String searchType = pageRequestDTO.getType();
        // If Keyword & SearchType Is Not Null
        if(keyword != null && searchType != null) {
            // split 해서 배열의 형태로 바꿉니다 tcw -> [t,c,w]
            String [] searchArr = searchType.split("");
            // () 우선 연산자를 만들어주는 객체 
            BooleanBuilder searchBuilder = new BooleanBuilder();
            for(String type : searchArr) {
                switch(type) {
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            }
            query.where(searchBuilder);
        }
        // Paging 
        this.getQuerydsl().applyPagination(pageable,query);
        // Board Group By 
        query.groupBy(board);
        JPQLQuery<BoardListRcntDTO> listQuery = query.select(Projections.bean(BoardListRcntDTO.class,
        board.bno,
        board.title,
        board.writer,
        board.content,
        reply.countDistinct().as("replyCount")));
        List<BoardListRcntDTO> listBoard = listQuery.fetch();
        // total Count 
        Long totalCount = listQuery.fetchCount();
        // PageResponseDTO 반환 
        return new PageResponseDTO<>(listBoard, totalCount, pageRequestDTO);
    }
}