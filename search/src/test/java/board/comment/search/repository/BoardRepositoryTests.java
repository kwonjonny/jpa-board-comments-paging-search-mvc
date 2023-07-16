package board.comment.search.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import board.comment.search.domain.Board;
import board.comment.search.dto.board.BoardListRcntDTO;
import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class BoardRepositoryTests {

    // 의존성 주입
    @Autowired
    private BoardRepository boardRepository;

    private static final String TEST_TITLE = "JunitTestTitle";
    private static final String TEST_CONTENT = "JunitTestContent";
    private static final String TEST_WRITER = "JunitTestWriter";
    private static final Long TEST_BNO = 3L;

    // BeforeEach 사용을 위한 Board Entity 정의
    Board board;

    @BeforeEach
    public void setUp() {
        board = Board.builder()
                .title(TEST_TITLE)
                .writer(TEST_WRITER)
                .content(TEST_CONTENT)
                .build();
    }

    // Create Repository Test
    @Test
    @Transactional
    @DisplayName("게시판 생성 레포지토리 테스트")
    public void createTestRepository() {
        log.info("=== Start Create Repostiroy Test ===");
        Board createdBoard = boardRepository.save(board);
        Assertions.assertNotNull(createdBoard);
        log.info("=== End Create Repsitory Test ===");
    }

    // Read Repository Test
    @Test
    @Transactional
    @DisplayName("게시판 상세 조회 레포지토리 테스트")
    public void readTestRepository() {
        // GIVEN
        log.info("=== Start Read Repository Test ===");
        // WHEN
        Optional<Board> readBoard = boardRepository.findById(TEST_BNO);
        Board board = readBoard.orElseThrow();
        // THEN
        log.info(board);
        Assertions.assertNotNull(readBoard, "readBoard Should Be Not Null");
        log.info("=== End Read Repository Test ===");
    }

    // Update Repository Test
    @Test
    @Transactional
    @DisplayName("게시판 업데이트 레포지토리 테스트")
    public void updateTestRepository() {
        // GIVEN
        log.info("=== Start Update Repository Test ===");
        // WHEN
        Optional<Board> readBoard = boardRepository.findById(TEST_BNO);
        Board board = readBoard.orElseThrow();
        board.changeContent(TEST_CONTENT);
        board.changeTitle(TEST_TITLE);
        board.changeWriter(TEST_WRITER);
        boardRepository.save(board);
        // THEN
        Optional<Board> resultBoard = boardRepository.findById(TEST_BNO);
        log.info(resultBoard);
        Assertions.assertTrue(resultBoard.isPresent(), "The board should be present");
        Assertions.assertEquals("JunitTestTitle", resultBoard.get().getTitle(), "The titles should match");
        Assertions.assertNotNull(resultBoard, "board Should Be Not Null");
        log.info("=== End Update Repository Test ===");
    }

    // Delete Repository Test 
    @Test
    @Transactional
    @DisplayName("게시판 삭제 레포지토리 테스트")
    public void deleteTestRepository() {
        // GIVEN 
        log.info("=== Start Delete Repository Test ===");
        // WHEN 
        Optional<Board> readBoard = boardRepository.findById(TEST_BNO);
        Board board = readBoard.orElseThrow();
        boardRepository.deleteById(TEST_BNO);
        // THEN 
        Optional<Board> deletedBoard = boardRepository.findById(TEST_BNO);
        Assertions.assertTrue(deletedBoard.isEmpty(), "deletedBoard Should Be Empty");
        log.info("=== End Delete Repository Test ===");
    }

    // List Repository Test 
    @Test
    @Transactional
    @DisplayName("게시판 리스트 With Reply Count 테스트")
    public void listTest() {
        log.info("=== Start List Board With Reply Count ===");
        // GIVEN 
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        // WHEN 
        PageResponseDTO<BoardListRcntDTO> responseDTO = boardRepository.searchDTORcnt(pageRequestDTO);
        // THEN 
        log.info(responseDTO);
        // Assertions.assertNotNull(responseDTO, "searchDTORcnt Should Be Not Null");
        log.info("=== End List Board With Reply Count ===");
    }
}
