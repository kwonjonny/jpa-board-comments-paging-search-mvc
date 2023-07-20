package board.comment.search.entity.file;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 자동 으로 FK 생성
@Getter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {
    private String fname;
    private int ord;
}
