package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor  @AllArgsConstructor
@Table(indexes={
        @Index(name="idx_buyer_gid", columnList = "gid")
})
public class Buyer extends BaseMemberEntity {

    @Id
    @GeneratedValue
    private Long buyerNo;       // 주문번호

    @Column(length=45, nullable = false)
    private String gid = UUID.randomUUID().toString();

    private boolean orderDone; // 주문 완료 여부

    @Column(length=40, nullable = false)
    private String buyerNm;         // 주문자명
    private Long buyerCnt;          // 주문한 상품의 수량

    private String bookNm;         // 주문한 상품 번호

    @Column(length=60)
    private String category;        // 주문한 상품 분류
    private String poster;          // 주문한 상품 저자
    private int price;              // 주문한 상품 가격
}
