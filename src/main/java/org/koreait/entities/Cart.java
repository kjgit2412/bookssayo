package org.koreait.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Column(length=40, nullable = false)
    private String buyerNm;         // 주문자명

    //  array로 지정 필요
    private String bookNm;         // 주문한 상품 번호
    private Long buyerCnt;          // 주문한 상품의 수량
}
