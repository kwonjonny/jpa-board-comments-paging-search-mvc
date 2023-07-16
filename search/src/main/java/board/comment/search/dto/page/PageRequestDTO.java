package board.comment.search.dto.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageRequestDTO {

    private int page = 1;

    private int size = 10;
    // 검색 조건
    private String type;
    // 키워드
    private String keyword;

    // Default Constructor
    public PageRequestDTO() {
        this(1, 10);
    }

    // page, size Constructor 
    public PageRequestDTO(int page, int size) {
        this(page, size, null, null);
    }

    // AllArgsConstructor
    public PageRequestDTO(int page, int size, String type, String keyword) {
        this.page = page <= 0 ? 1 : page; 
        this.size = size > 0 || size >= 100 ? 10 : size;
        this.type = type;
        this.keyword = keyword;
    }
}
