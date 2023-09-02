package org.koreait.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
/**
 * 모든 회원 관련 엔티티 클래스에서 공통으로 사용하는 필드 및 기능을 정의한 추상 클래스.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMemberEntity extends BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy; // 생성한 사용자의 정보를 저장하는 필드

    @LastModifiedBy
    @Column(insertable = false)
    private String modifiedBy; // 수정한 사용자의 정보를 저장하는 필드
}
