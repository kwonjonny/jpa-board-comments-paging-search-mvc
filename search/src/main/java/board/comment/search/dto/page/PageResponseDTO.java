package board.comment.search.dto.page;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;

// 제네릭은 complie하면 object로 나온다.
@Getter
@Setter
public class PageResponseDTO<E> {
    
    private List<E> dtoList;

    private long totalCount;

    private List<Integer> pageNums;

    private boolean prev, next;

    // 포함관계
    private PageRequestDTO pageRequestDTO;

    private int page, size, start, end;

    // JPA pageImpl이랑 비슷
    public PageResponseDTO(List<E> dtoList, long totalCount, PageRequestDTO pageRequestDTO){

        this.dtoList = dtoList;
        this.totalCount = totalCount;
        this.pageRequestDTO = pageRequestDTO;

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        int tempEnd = (int)(Math.ceil(page / 10.0) * 10);

        this.start = tempEnd - 9;
        this.prev = this.start != 1;

        //20 17.8
        int realEnd = (int)(Math.ceil (totalCount/(double)size));

        this.end = tempEnd > realEnd ? realEnd : tempEnd;

        this.next = (this.end * this.size) < totalCount;

        this.pageNums = IntStream.rangeClosed(start,end).boxed().toList();
    }

}
