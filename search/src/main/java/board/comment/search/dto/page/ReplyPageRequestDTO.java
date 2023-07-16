package board.comment.search.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyPageRequestDTO extends PageRequestDTO {
    private Long bno;

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 20;
    // 댓글의 마지막 페이지 여부 
    private boolean last;
}
