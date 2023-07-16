package board.comment.search.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * @Column = nullable 허용하지 않는다 
 * @Table = name 지정 
 * @GeneerationType.IDENTITY = AutoIncrement 
 * 
 */
@Entity
@Getter
@Builder
@ToString
@Table(name = "t_board")
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {
    
    // PrimaryKey AutoIncerment 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeWriter(String writer) {
        this.writer = writer;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
