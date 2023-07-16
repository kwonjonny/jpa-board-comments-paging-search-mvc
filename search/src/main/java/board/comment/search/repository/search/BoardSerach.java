package board.comment.search.repository.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import board.comment.search.dto.board.BoardListRcntDTO;
import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;

public interface BoardSerach {
    // List Board
    PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO pageRequestDTO);

    // Sort 조건 Default
    default Pageable makePageable(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("bno").descending());
        return pageable;
    }
}
