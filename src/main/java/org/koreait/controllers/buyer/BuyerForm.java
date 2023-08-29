package org.koreait.controllers.buyer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import org.koreait.commons.constants.BookStatus;
import org.koreait.commons.constants.BuyerStatus;

import java.util.UUID;

public class BuyerForm {

    private String mode;
    //mode : all - 모두, done - 주문완료, undone : 장바구니 상태

    private Long buyerNo;       // 주문번호
    private String buyerNm;         // 주문자명
    private Long buyerCnt;          // 주문한 상품의 수량

    @NotBlank
    private String gid = UUID.randomUUID().toString();

    private int sumPrice;
    private String status = BuyerStatus.ORDER.name();


    private int stock;

    private long listOrder;
    private String waybillNo; // 운송장번호


}
