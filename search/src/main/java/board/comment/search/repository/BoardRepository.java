package board.comment.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import board.comment.search.domain.Board;
import board.comment.search.repository.search.BoardSerach;

/*
 * JpaRepository 의 Board Entity Long Type의 Primary Key 
 * BoardSearch 인터페이스 QeuryDsl Using 
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardSerach {
    
   
}
