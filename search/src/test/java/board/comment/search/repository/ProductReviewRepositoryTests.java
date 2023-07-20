package board.comment.search.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import board.comment.search.entity.Product;
import board.comment.search.entity.ProductReview;
import board.comment.search.entity.file.ProductImage;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class ProductReviewRepositoryTests {

    // 의존성 주입
    @Autowired
    private ProductReviewRepository productReviewRepository;

    private static final Long TEST_PNO = 7L;
    private static final int TEST_SCORE = (int) (Math.random() * 5) + 1;
    private static final String TEST_CONTENT = "Junit_Test_Content";
    private static final String TEST_REVIEWER = "Junit_Test_Reviewr";
    private static final Long TEST_RNO = 10L;

    private static final String TEST_PRODUCT_FILE_NAME = "Junit.jpg";
    private String uuid;

    private ProductReview productReview;
    private Product product;
    private ProductImage productImage;

    @BeforeEach
    public void setup() {
        // 셋업 로직 작성
        uuid = UUID.randomUUID().toString();

        product = Product.builder()
                .pno(TEST_PNO)
                .build();

        productReview = ProductReview.builder()
                .content(TEST_CONTENT)
                .reviewer(TEST_REVIEWER)
                .score(TEST_SCORE)
                .product(product)
                .build();
    }

    // Create ProductReview Repository Test
    @Test
    @Transactional
    @DisplayName("리뷰 생성 테스트")
    public void createReviewTest() {
        // GIVEN
        log.info("=== Start Create Review Repository Test ===");
        productReview.addImage(uuid + "_" + TEST_PRODUCT_FILE_NAME);
        // WHEN
        ProductReview createdProduct = productReviewRepository.save(productReview);
        // THEN
        Optional<ProductReview> result = productReviewRepository.findById(TEST_RNO);
        Assertions.assertNotNull(result, "result Should Be Not Null");
        Assertions.assertEquals(TEST_SCORE, createdProduct.getScore());
        Assertions.assertEquals("Junit_Test_Content", createdProduct.getContent());
        Assertions.assertEquals("Junit_Test_Reviewr", createdProduct.getReviewer());
        Assertions.assertEquals(uuid + "_" + TEST_PRODUCT_FILE_NAME, createdProduct.getImages().get(0).getFname());
        log.info("=== End Create Review Repository Test ===");
    }

    // Update ProductReivew Repository Test
    @Test
    @Transactional
    @DisplayName("리뷰 업데이트 테스트")
    public void updateReviewTest() {
        // GIVEN
        log.info("=== Start Update Review Repository Test ===");
        // WHEN
        Optional<ProductReview> result = productReviewRepository.findById(TEST_RNO);
        ProductReview productReview = result.orElseThrow();
        productReview.changeContet(TEST_CONTENT);
        productReview.changeReview(TEST_REVIEWER);
        productReview.changeScore(TEST_SCORE);
        productReview.addImage(uuid + "_" + TEST_PRODUCT_FILE_NAME);
        // THEN
        Assertions.assertNotNull(productReview, "productReview Should Be Not Null");
        Assertions.assertEquals(TEST_CONTENT, productReview.getContent());
        Assertions.assertEquals(TEST_REVIEWER, productReview.getReviewer());
        Assertions.assertEquals(TEST_SCORE, productReview.getScore());
        log.info("=== End Update Review Repository Test ===");
    }

    // Read ProductReview Repository Test
    @Test
    @Transactional
    @DisplayName("리뷰 상세 조회 테스트")
    public void readReviewTest() {
        // GIVEN
        log.info("=== Start Read Review Repository Test ===");
        // WHEN
        Optional<ProductReview> result = productReviewRepository.findById(14L);
        // THEN
        Assertions.assertNotNull(result, "result Should Be Not Null");
        log.info("=== End Read Review Repository Test ===");
    }
}
