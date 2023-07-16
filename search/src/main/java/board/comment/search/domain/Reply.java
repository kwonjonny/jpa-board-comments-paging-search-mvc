package board.comment.search.domain;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_reply")
@ToString(exclude = "board")
public class Reply extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @Column(length = 200, nullable = false)
    private String replyText;

    @Column(length = 50, nullable = false)
    private String replyer;

    private String replyFile;

    // ManyToOne 적용 
    // Fetch Type Lazy
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    public void changeText(String replyText) {
        this.replyText = replyText;
    }

    public void changeReplyer(String replyer) {
        this.replyer = replyer;
    }

    public void changeReplyFile(String replyFile) {
        this.replyFile = replyFile;
    }
}
