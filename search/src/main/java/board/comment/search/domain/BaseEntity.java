package board.comment.search.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/*
 * @MappedSuperClass = Entity Manager 에게 Table로 생성 하지 말라고 알림 
 * @EntityLisnters = modDate 와 regDate 를 다른 클래스에 상속받아 사용할수있게 설정
 */
@Getter
@MappedSuperclass   
@EntityListeners(value = {AuditingEntityListener.class})    
public class BaseEntity {

    // Entity Manaer 에게 regDate는 update 하지 말라고 알림 
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;
    
}
