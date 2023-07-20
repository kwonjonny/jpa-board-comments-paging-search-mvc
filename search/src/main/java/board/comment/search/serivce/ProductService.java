package board.comment.search.serivce;

import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;
import board.comment.search.dto.product.ProductDTO;
import board.comment.search.dto.product.ProductListDTO;

public interface ProductService {

    // List Service Product 
    PageResponseDTO<ProductListDTO> listProduct(PageRequestDTO pageRequestDTO);

    // Create Product 
    Long createProduct(ProductDTO productDTO);

    // Read Product 
    ProductDTO readProduct(Long pno);

    // Delete Product 
    void deleteProduct(Long pno);

    // Update Product 
    void updateProduct(ProductDTO productDTO);
}
