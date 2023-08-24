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
@NoArgsConstructor  @AllArgsConstructor
public class Buyer extends BaseMemberEntity {

    @Id
    @GeneratedValue
    private Long buyerNo;       // 주문번호

    @Column(length=40, nullable = false)
    private String buyerNm;     // 주문자명

    private String buyerBookNo;         // 주문한 상품의 번호
    private Long buyerBookCnt;          // 주문한 상품의 수량
    private String buyerBookSubject;    // 주문한 상품의 저자
    private String buyerBookPrice;      // 주문한 상품의 가격
}
