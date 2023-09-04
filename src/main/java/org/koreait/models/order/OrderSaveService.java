package org.koreait.models.order;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.constants.OrderStatus;
import org.koreait.commons.constants.PaymentType;
import org.koreait.controllers.orders.OrderForm;
import org.koreait.entities.*;
import org.koreait.repositories.order.OrderInfoRepository;
import org.koreait.repositories.order.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderSaveService {
    private final CartService cartService;
    private final OrderInfoRepository infoRepository;
    private final OrderItemRepository itemRepository;
    private final MemberUtil memberUtil;

    public void save(OrderForm form) {
        List<Long> cartNos = form.getCartNo();
        List<Cart> cartItems = cartService.getList(cartNos);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new CartItemNotFoundException();
        }

        int payPrice = cartService.getTotalPrice(cartItems);

        /** 주문서 정보 저장 S */
        OrderInfo orderInfo = OrderInfo.builder()
                .orderName(form.getOrderName())
                .orderEmail(form.getOrderEmail())
                .orderMobile(form.getOrderMobile())
                .receiverName(form.getReceiverName())
                .receiverMobile(form.getReceiverMobile())
                .address(form.getAddress())
                .zonecode(form.getZonecode())
                .addressSub(form.getAddressSub())
                .paymentType(PaymentType.valueOf(form.getPaymentType()))
                .payPrice(payPrice)
                .member(memberUtil.getEntity())
                .build();

        infoRepository.saveAndFlush(orderInfo);
        /** 주문서 정보 저장 E */

        /** 주문 상품 정보 저장 S */
        List<OrderItem> items = new ArrayList<>();
        for (Cart cartItem : cartItems) {
            Book book = cartItem.getBook();
            Category category = book.getCategory();
            OrderItem item = OrderItem.builder()
                    .status(OrderStatus.READY)
                    .ea(cartItem.getEa())
                    .book(book)
                    .price(book.getPrice())
                    .cateNm(category == null ? null : category.getCateNm())
                    .bookNm(book.getBookNm())
                    .totalPrice(cartItem.getTotalPrice())
                    .cartNo(cartItem.getCartNo())
                    .orderInfo(orderInfo)
                    .build();
            items.add(item);
        }

        itemRepository.saveAllAndFlush(items);
        form.setId(orderInfo.getId());      // 객체참조를 위해서
        /** 주문 상품 정보 저장 E */
    }
}
