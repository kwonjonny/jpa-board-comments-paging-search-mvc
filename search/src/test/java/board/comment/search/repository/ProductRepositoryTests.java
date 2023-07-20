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

import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;
import board.comment.search.dto.product.ProductListDTO;
import board.comment.search.entity.Product;
import lombok.extern.log4j.Log4j2;

// Product Repository Tests Class 
@Log4j2
@SpringBootTest
public class ProductRepositoryTests {

    // 의존성 주입
    @Autowired(required = false)
    private ProductRepository productRepository;

    private static final Long TEST_PNO = 7L;
    private static final String TEST_PRODUCT_NAME = "Junit_Test_Product_Name";
    private static final String TEST_PRODUCT_DESC = "Junit_Test_Product_Desc";
    private static final String TEST_PORUDCT_WRITER = "Junit_Test_Product_Writer";
    private static final String TEST_PRODUCT_FILE_NAME = "Junit.jpg";
    private static final int TEST_PRODUCT_PRICE = 100_000;
    private String uuid;

    private Product product;

    @BeforeEach
    public void setUp() {

        uuid = UUID.randomUUID().toString();

        product = Product.builder()
                .pname(TEST_PRODUCT_NAME)
                .pdesc(TEST_PRODUCT_DESC)
                .writer(TEST_PORUDCT_WRITER)
                .price(TEST_PRODUCT_PRICE)
                .build();
    }

    // Create Test Repository
    @Test
    @Transactional
    @DisplayName("상품 생성 테스트 레포지토리")
    public void createTestProductRepository() {
        // GVIEN
        log.info("=== Start Create Product Test Repository ===");
        product.addImage(uuid + "_" + TEST_PRODUCT_FILE_NAME);
        // WHEN
        Product savedProduct = productRepository.save(product);
        // THEN
        Assertions.assertNotNull(savedProduct, "savedProduct Should Be Not Null");
        Assertions.assertEquals(TEST_PRODUCT_DESC, savedProduct.getPdesc());
        Assertions.assertEquals(TEST_PORUDCT_WRITER, savedProduct.getWriter());
        Assertions.assertEquals(TEST_PRODUCT_PRICE, savedProduct.getPrice());
        Assertions.assertEquals(uuid + "_" + TEST_PRODUCT_FILE_NAME, savedProduct.getImages().get(0).getFname());
        log.info("=== End Create Product Test Repository ===");
    }

    // Update Test Repository
    @Test
    @Transactional
    @DisplayName("상품 업데이트 테스트 레포지토리")
    public void updateTestProductRepository() {
        // GIVEN
        log.info("=== Start Update Product Test Repository");
        // WHEN 
        Optional<Product> result = productRepository.findById(TEST_PNO);
        Product product = result.orElseThrow();
        product.changePrice(TEST_PRODUCT_PRICE);
        product.changePdesc(TEST_PRODUCT_DESC);
        product.changePname(TEST_PRODUCT_FILE_NAME);
        product.addImage(uuid + "_" + TEST_PRODUCT_FILE_NAME);
        product.changeDeleteFlag(false);
        // THEN
        Assertions.assertNotNull(product, "savedProduct Should Be Not Null");
        Assertions.assertEquals(TEST_PRODUCT_DESC, product.getPdesc());
        Assertions.assertEquals(TEST_PORUDCT_WRITER, product.getWriter());
        Assertions.assertEquals(TEST_PRODUCT_PRICE, product.getPrice());
        log.info("=== End Update Product Test Repository ===");
    }

    // List Test Repository
    @Test
    @Transactional
    @DisplayName("상품 리스트 with 리뷰")
    public void listTestProductRepository() {
        // GIVEN
        log.info("=== Start List Repository ===");
        // WHEN
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<ProductListDTO> result = productRepository.listWithReview(pageRequestDTO);
        // THEN
        log.info(result.getDtoList());
        Assertions.assertNotNull(result, "result Should Be Not Null");
        log.info("==== End List Repository ===");
    }

    // Delete Test Repository
    @Test
    @Transactional
    @DisplayName("상품 삭제 테스트 레포지토리")
    public void deleteTestRepository() {
        // GIVEN
        log.info("=== Start Delete Repository ===");
        // WHEN
        productRepository.deleteById(TEST_PNO);
        // THEN
        Optional<Product> result = productRepository.findById(TEST_PNO);
        Assertions.assertFalse(result.isPresent(), "result Should Be Not In Database");
    }

    // Read Test Repository
    @Test
    @Transactional
    @DisplayName("상품 읽기 테스트 레포지토리")
    public void readTestRepository() {
        // GIVEN
        log.info("=== Start Read Repository ===");
        // WHEN
        Optional<Product> result = productRepository.findById(TEST_PNO);
        // THEN
        Assertions.assertNotNull(result, "result Should Be Not Null");
        log.info("=== End Read Repository ===");
    }
}
