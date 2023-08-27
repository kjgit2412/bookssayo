package org.koreait.controllers.buyer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class BuyerForm {

    private String mode;
    //mode : all - 모두, done - 주문완료, undone : 장바구니 상태

    private Long buyerNo;       // 주문번호
    private String buyerNm;         // 주문자명
    private Long buyerCnt;          // 주문한 상품의 수량


    @NotBlank
    private String gid = UUID.randomUUID().toString();

    @NotBlank
    private String bookNm;
    private String category;

    private int price;
    private int stock;

    private String status;

    private long listOrder;

    private String poster;          // 주문한 상품 저자

}
