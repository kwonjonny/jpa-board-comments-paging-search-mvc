package board.comment.search.dto.product;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProductDTO {
    
    @Comment("상품 번호")
    private Long pno;

    @NotNull
    @Comment("상품 이름")
    private String pname;

    @NotNull
    @Comment("상품 설명")
    private String pdesc;

    @NotBlank
    @Comment("상품 가격")
    private int price;

    @NonNull
    @Comment("작성자")
    private String writer;

    @Builder.Default
    private List<String> images = new ArrayList<>();

    /*
     * Enginx Uploder Util 에서 쓰이는 MultipartFile
     */
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();
}
