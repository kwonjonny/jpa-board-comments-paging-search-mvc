package board.comment.search.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 댓글 개수까지 띄워주는 Board List DTO 
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardListRcntDTO {
    private Long bno;
    private String title;
    private String writer;
    private String content;
    // 댓글 개수 
    private Long replyCount;
}
