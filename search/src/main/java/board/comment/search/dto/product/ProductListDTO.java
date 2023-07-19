package board.comment.search.dto.product;

import org.hibernate.annotations.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDTO {

    @Comment("상품 번호")
    private Long pno;
    
    @Comment("상품 이름")
    private String pname;
    
    @Comment("상품 가격")
    private int price;

    @Comment("파일 이름")
    private String fname;

    @Comment("리뷰 개수")
    private long reviewCnt;

    @Comment("리뷰 평점")
    private double reviewAvg;
    
}
