package board.comment.search.entity.file;

import board.comment.search.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String content;

    private String reviewer;

    private int score;

    // 한 리뷰는 한 상품에 달린다.
    // @ManyToOne을 달아준다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    
}

