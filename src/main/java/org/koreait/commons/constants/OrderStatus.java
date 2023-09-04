package org.koreait.commons.constants;


import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

        READY("주문접수"), // 주문접수
        PAYMENT("입금확인"), // 입금확인
        PREPARE("상품준비중"), // 상품준비중
        DELIVERY("배송중"), // 배송중
        ARRIVAL("배송완료"), // 배송완료
        DONE("주문완료"), // 주문완료
        CANCEL("주문취소"), // 주문취소
        REFUND("환불"), // 환불
        EXCHANGE("교환"); // 교환

        private String title;
        OrderStatus(String title) { this.title = title;}
        public String getTitle() {
                return title;
        }

        /*
        public String getString() {
                return Utils.getMessage("BuyerStatus." + name(), "common");
        }
        */


        public static List<String[]> getList() {
                return Arrays.asList(
                        new String[]{READY.name(),READY.getTitle()},
                        new String[]{DONE.name(),DONE.getTitle()},
                        new String[]{PAYMENT.name(),PAYMENT.getTitle()},
                        new String[]{PREPARE.name(),PREPARE.getTitle()},
                        new String[]{DELIVERY.name(),DELIVERY.getTitle()},
                        new String[]{ARRIVAL.name(),ARRIVAL.getTitle()},
                        new String[]{CANCEL.name(),CANCEL.getTitle()},
                        new String[]{REFUND.name(),REFUND.getTitle()},
                        new String[]{EXCHANGE.name(),EXCHANGE.getTitle()}
                );
        }
}
