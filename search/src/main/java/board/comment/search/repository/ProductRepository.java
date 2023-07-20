package board.comment.search.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import board.comment.search.entity.Product;
import board.comment.search.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch{

    // image까지 한번에 가져오기 위해서 작성 => Product 1번 이미지한번 transactional로 묶어서 2번 쿼리를 날리면 효율이 떨어진다.
    // outer join 해서 한번에 가져온다 
    @EntityGraph(attributePaths = "images")
    @Query("select p from Product p where p.delFlag = false and p.pno = :pno")
    Product selectOne(@Param("pno") Long pno);
}
