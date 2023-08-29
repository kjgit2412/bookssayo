package org.koreait.models.buyer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.BuyerStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerSearch {
    private int page = 1; // 페이지 번호
    private int limit = 10; // 1페이지당 레코드 갯수

    private List<BuyerStatus> statuses;
    private BuyerStatus status;

    private String buyerNm;   // 주문자명
    private Long buyerNo;      // 주문번호

    private String sopt;    // 검색 옵션
    private String skey;    // 검색 키워드

    private String sort;    // 정렬
}
