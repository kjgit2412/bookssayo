package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Category extends BaseMemberEntity {
    @Id
    @Column(length=30)
    private String cateCd;

    @Column(length=60, nullable = false)
    private String cateNm;

    @Column(name="_use")
    private boolean use;

    private long listOrder; // 진열 순서
}
