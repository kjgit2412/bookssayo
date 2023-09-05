package org.koreait.entities;

import jakarta.persistence.*;
import lombok.*;
import org.koreait.commons.constants.OrderStatus;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long cartNo;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="bookNo")
    private Book book;

    private String cateNm;

    @Column(length=100, nullable = false)
    private String bookNm;
    private int price;
    private int ea = 1;
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(length=15, nullable = false)
    private OrderStatus status = OrderStatus.READY;

    @Column(length=50)
    private String deliveryCompany; // 배송 업체
    @Column(length=50)
    private String invoice; // 운송장 번호
    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="orderNo")
    private OrderInfo orderInfo;
}
