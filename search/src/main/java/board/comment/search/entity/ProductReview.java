package board.comment.search.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import board.comment.search.entity.file.ReviewImage;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@ToString(exclude = { "product", "images" })
public class ProductReview {

    @Id
    @Comment("리뷰 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @NotNull
    @Comment("리뷰 콘텐츠")
    private String content;

    @NotNull
    @Comment("리뷰 한 사람")
    private String reviewer;

    @NotNull
    @Comment("리뷰 점수")
    private int score;

    // 한 리뷰는 한 상품에 달린다.
    // @ManyToOne을 달아준다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    /*
     * ElementCollection
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ReviewImage> images = new ArrayList<>();

    /*
     * addImage ord값 증가 시킨다
     */
    public void addImage(String name) {
        ReviewImage rImage = ReviewImage.builder()
                .fname(name)
                .ord(images.size())
                .build();
        images.add(rImage);
    }

    /*
     * File Clear
     */
    public void clearIamges() {
        images.clear();
    }

    public void changeContet(String content) {
        this.content = content;
    }

    public void changeReview(String reviewer) {
        this.reviewer = reviewer;
    }

    public void changeScore(int score) {
        this.score = score;
    }

}
