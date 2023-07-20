package board.comment.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import board.comment.search.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    
}
