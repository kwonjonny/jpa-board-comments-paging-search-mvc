package board.comment.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import board.comment.search.domain.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
}
