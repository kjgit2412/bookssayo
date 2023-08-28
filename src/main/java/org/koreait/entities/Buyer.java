package org.koreait.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.BookStatus;
import org.koreait.commons.constants.BuyerStatus;

import java.util.List;
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
    private Long buyerNo;           // 주문번호

    @Column(length=45, nullable = false)
    private String gid = UUID.randomUUID().toString();

    private boolean orderDone;      // 주문 완료 여부

    @Column(length=40, nullable = false)
    private String buyerNm;         // 주문자명

    private Long buyerCnt;          // 주문한 상품의 수량
    private Long sumPrice;          // 주문한 총 금액

    @Enumerated(EnumType.STRING)
    @Column(length=25, nullable = false)
    private BuyerStatus status = BuyerStatus.ORDER;

///    private List<BuyBook> buyBooks;     // 주문한 책들의 list

    @Transient
    private String waybillNo; // 운송장번호

}
