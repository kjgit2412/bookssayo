package org.koreait.controllers.orders;

import lombok.Data;
import org.koreait.commons.constants.OrderStatus;

@Data
public class OrderSearch {
    private int page = 1;
    private int limit = 20;
    private String sort = "createdAt_DESC";

    /** 단일 주문번호 조회 */
    private Long id;
    /** 여러 주문번호 조회 - in */
    private Long[] ids;
    private String sopt = "all"; // 검색 옵션
    private String skey; // 검색 키워드

    private OrderStatus[] statuses;
    private OrderStatus status;
}
