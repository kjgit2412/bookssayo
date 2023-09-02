package org.koreait.commons.constants;

import org.koreait.commons.Utils;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

        READY, // 주문접수
        PAYMENT, // 입금확인
        PREPARE, // 상품준비중
        DELIVERY, // 배송중
        ARRIVAL, // 배송완료
        DONE, // 주문완료
        CANCEL, // 주문취소
        REFUND, // 환불
        EXCHANGE; // 교환


        public String getString() {
                return Utils.getMessage("BuyerStatus." + name(), "common");
        }

        public static List<String[]> getList() {
                return Arrays.asList(
                        new String[]{READY.name(),READY.getString()},
                        new String[]{DONE.name(),DONE.getString()},
                        new String[]{PAYMENT.name(),PAYMENT.getString()},
                        new String[]{PREPARE.name(),PREPARE.getString()},
                        new String[]{DELIVERY.name(),DELIVERY.getString()},
                        new String[]{ARRIVAL.name(),ARRIVAL.getString()},
                        new String[]{CANCEL.name(),CANCEL.getString()},
                        new String[]{REFUND.name(),REFUND.getString()},
                        new String[]{EXCHANGE.name(),EXCHANGE.getString()}
                );
        }
}
