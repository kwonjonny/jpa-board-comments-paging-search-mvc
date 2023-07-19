package board.comment.search.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import board.comment.search.entity.file.ProductImage;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "images")
public class Product {

    @Id
    @Comment("상품 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    @NotNull
    @Comment("상품 이름")
    private String pname;

    @NotNull
    @Comment("상품 설명")
    private String pdesc;

    @NotNull
    @Comment("작성자")
    private String writer;

    @NotNull
    @Comment("상품 가격")
    private int price;

    @Comment("삭제 여부")
    private boolean delFlag;

    /*
     * ElementCollection
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    /*
     * addImage ord값 증가 시킨다
     */
    public void addImage(String name) {
        ProductImage pImage = ProductImage.builder()
                .fname(name)
                .ord(images.size())
                .build();
        images.add(pImage);
    }

    /*
     * File Clear
     */
    public void clearImages() {
        images.clear();

    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changePname(String pname) {
        this.pname = pname;
    }

    public void changePdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void changeDeleteFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }
}
