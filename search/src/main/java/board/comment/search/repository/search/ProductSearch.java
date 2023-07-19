package board.comment.search.repository.search;

import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;
import board.comment.search.dto.product.ProductListDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO);
}
