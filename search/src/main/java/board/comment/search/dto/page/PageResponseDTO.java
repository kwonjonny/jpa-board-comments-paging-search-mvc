package board.comment.search.dto.page;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponseDTO<E> {
    private List<E> dtoList;
    private Long totalCount;
    private List<Integer> pageNums;
    private boolean prev;
    private boolean next;

    // 조합을 사용하기 위한 PageRequestDTO = page, size, kewyord 를 사용
    private PageRequestDTO requestDTO;

    // Current page size start end
    private int size;
    private int page;
    private int start;
    private int end;

    // 생성자 기본적으로 list<E> 와 Long totalCount pageReuqetDTO는 꼭 필여한 조건
    public PageResponseDTO(List<E> dtoList, Long totalCount, PageRequestDTO pageRequestDTO) {
        this.dtoList = dtoList;
        this.totalCount = totalCount;
        this.requestDTO = pageRequestDTO;
        // pageRequestDTO안에 있는 size와 page를 가져온다
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        // 가정 페이지 번호가 13 이면 1.3 Math.Ceil 해서 올린다 그럼 2.0
        // 2.0 * 10 = 20
        int tempEnd = (int) Math.ceil(page / 10.0) * 10;

        // 페이지번호 20 - 9 = 11 이다
        this.start = tempEnd - 9;
        this.prev = this.start != 1;

        // 가정 tempEnd 20이다 데이터는 178건이면 사이즈로 나눈다 그럼 페이지의 개수가된다
        // 나누면 17.8
        // Math.ceil로 올려준다
        int realEnd = (int) (Math.ceil(totalCount / (double) size));

        this.end = tempEnd > realEnd ? realEnd : tempEnd;

        // next 의 유무 end 와 size 곱이 200 인데 totalCOunt가 201이면 next True
        this.next = (this.end * this.size) < totalCount;

        this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
    }
}
