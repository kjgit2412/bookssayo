package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {

    // 장바구니

    @Id   @GeneratedValue
    private Long CartNo;

    @Column(name="_uid")
    private int uid;

    @Column(length=10, nullable = false, name="_mode")
    private String mode = "direct"; // 바로 구매

    private int ea = 1;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bookNo")
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;
}
