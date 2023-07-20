package board.comment.search.service;

import java.util.Collections;
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
import board.comment.search.dto.product.ProductDTO;
import board.comment.search.dto.product.ProductListDTO;
import board.comment.search.serivce.ProductService;
import lombok.extern.log4j.Log4j2;

// Product Service Test Class 
@Log4j2
@SpringBootTest
public class ProductServiceTests {

    // 의존성 주입
    @Autowired
    private ProductService productService;

    private static final Long TEST_PNO = 7L;
    private static final String TEST_PRODUCT_NAME = "Junit_Test_Product_Name";
    private static final String TEST_PRODUCT_DESC = "Junit_Test_Product_Desc";
    private static final String TEST_PORUDCT_WRITER = "Junit_Test_Product_Writer";
    private static final String TEST_PRODUCT_FILE_NAME = "Junit.jpg";
    private static final int TEST_PRODUCT_PRICE = 100_000;
    private String uuid;

    private ProductDTO productDTO;
    private ProductDTO produtUpdateDTO;

    @BeforeEach
    public void setUp() {
        uuid = UUID.randomUUID().toString();

        productDTO = ProductDTO.builder()
                .writer(TEST_PORUDCT_WRITER)
                .pname(TEST_PRODUCT_NAME)
                .pdesc(TEST_PRODUCT_DESC)
                .price(TEST_PRODUCT_PRICE)
                .images(Collections.singletonList(uuid + "_" + TEST_PRODUCT_FILE_NAME))
                .build();

        produtUpdateDTO = ProductDTO.builder()
                .pno(TEST_PNO)
                .writer(TEST_PORUDCT_WRITER)
                .pname(TEST_PRODUCT_NAME)
                .pdesc(TEST_PRODUCT_DESC)
                .price(TEST_PRODUCT_PRICE)
                .images(Collections.singletonList(uuid + "_" + TEST_PRODUCT_FILE_NAME))
                .build();
    }

    // Create Test Service
    @Test
    @Transactional
    @DisplayName("상품 생성 테스트 서비스")
    public void createTestProductService() {
        // GIVEN
        log.info("=== Start Create Product Service ===");
        // WHEN
        Long createdProduct = productService.createProduct(productDTO);
        // THEN
        ProductDTO productDTO = productService.readProduct(createdProduct);
        Assertions.assertEquals(productDTO.getPname(), TEST_PRODUCT_NAME);
        Assertions.assertEquals(productDTO.getPdesc(), TEST_PRODUCT_DESC);
        Assertions.assertEquals(productDTO.getPrice(), TEST_PRODUCT_PRICE);
        log.info("=== End Create Product Service ===");
    }

    // Update Test Service
    @Test
    @Transactional
    @DisplayName("상품 업데이트 테스트 서비스")
    public void updateTestProductService() {
        // GIVEN
        log.info("=== Start Update Product Service ===");
        // WHEN
        productService.updateProduct(produtUpdateDTO);
        // THEN
        ProductDTO productDTO = productService.readProduct(TEST_PNO);
        Assertions.assertEquals(productDTO.getPname(), TEST_PRODUCT_NAME);
        Assertions.assertEquals(productDTO.getPdesc(), TEST_PRODUCT_DESC);
        Assertions.assertEquals(productDTO.getPrice(), TEST_PRODUCT_PRICE);
        log.info("=== End Update Product Service ===");
    }

    // Delete Test Service
    @Test
    @Transactional
    @DisplayName("상품 삭제 테스트 서비스")
    public void deleteTestProductService() {
        // GIVEN
        log.info("=== Start Delete Product Service ===");
        // WHEN
        productService.deleteProduct(TEST_PNO);
        // THEN
        Assertions.assertThrows(
                NullPointerException.class,
                () -> productService.readProduct(TEST_PNO));
        log.info("=== End Delete Product Service ===");
    }

    // List Test Service 
    @Test
    @Transactional
    @DisplayName("상품 리스트 테스트 서비스")
    public void listTestProductService() {
        // GIVEN 
        log.info("=== Start List Product Service ===");
        // WHEN 
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResponseDTO<ProductListDTO> pageResponseDTO = productService.listProduct(pageRequestDTO);
        // THEN 
        log.info(pageResponseDTO.getDtoList());
        log.info("=== End List Product Service ===");
    }

    // Read Test Service 
    @Test
    @Transactional
    @DisplayName("상품 상세 조회 테스트 서비스")
    public void readTeestProductTest() {
        // GIVEN 
        log.info("=== Start Read Product Service ===");
        // WHEN 
        ProductDTO productDTO = productService.readProduct(TEST_PNO);
        // THEN 
        Assertions.assertNotNull(productDTO, "productDTO Should Be Not Null");
        log.info("=== End Read Product Service ===");
    }
}
