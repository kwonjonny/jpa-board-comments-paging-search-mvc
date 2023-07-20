package board.comment.search.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import com.mysql.cj.x.protobuf.MysqlxCrud.Projection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import board.comment.search.dto.page.PageRequestDTO;
import board.comment.search.dto.page.PageResponseDTO;
import board.comment.search.dto.product.ProductListDTO;
import board.comment.search.entity.Product;
import board.comment.search.entity.QProduct;
import board.comment.search.entity.QProductReview;
import board.comment.search.entity.file.QProductImage;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl(){
        super(Product.class);
    }

    // reivew search 
    @Override
    public PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO) {
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        QProductReview review = QProductReview.productReview;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.images,productImage);
        query.leftJoin(review).on(review.product.eq(product));

        // group by X ord가 0인값만 가져오게 함 => 썸네일
        query.where(productImage.ord.eq(0));
        query.where(product.delFlag.eq(Boolean.FALSE));

        int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage()-1;

        Pageable pageable =
            PageRequest.of(pageNum,pageRequestDTO.getSize(), Sort.by("pno").descending()); 

        this.getQuerydsl().applyPagination(pageable, query);

        // 상품 단위로 그룹바이 
        query.groupBy(product);

        JPQLQuery<ProductListDTO> dtoQuery = 
            query.select(
                Projections.bean(ProductListDTO.class,
                product.pno,
                product.pname,
                product.price,
                productImage.fname.min().as("fname"),  // min 그룹 함수 
                review.score.avg().as("reviewAvg"),    // reviewAvg 그룹 핑 
                review.count().as("reivewCnt"))         // reviewCnt 그룹 핑 
            );

            List<ProductListDTO> dtoList = dtoQuery.fetch();

            long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }

    
    
}
