package org.koreait.models.order;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ListData;
import org.koreait.commons.Pagination;
import org.koreait.commons.Utils;
import org.koreait.commons.constants.OrderStatus;
import org.koreait.controllers.orders.OrderForm;
import org.koreait.controllers.orders.OrderSearch;
import org.koreait.entities.OrderInfo;
import org.koreait.entities.OrderItem;
import org.koreait.entities.QOrderInfo;
import org.koreait.entities.QOrderItem;
import org.koreait.models.books.BookInfoService;
import org.koreait.repositories.order.OrderInfoRepository;
import org.koreait.repositories.order.OrderItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoRepository repository;
    private final OrderItemRepository itemRepository;

    private final EntityManager em;
    private final BookInfoService bookInfoService;
    private final HttpServletRequest request;

    public OrderInfo get(Long id) {
        OrderInfo data = repository.findById(id).orElseThrow(OrderNotFoundException::new);

        List<OrderItem> items = data.getOrderItems();
        if (items != null && !items.isEmpty()) {
            String bookNm = items.get(0).getBookNm();
            bookNm = items.size() > 1 ? bookNm += "외" + (items.size() - 1) + "건" : bookNm;
            data.setBookNm(bookNm);

            items.stream().forEach(i -> bookInfoService.addFileInfo(i.getBook()));
        }

        return data;
    }

    public OrderForm getForm(Long orderNo) {
        OrderInfo data = get(orderNo);

        OrderForm form = new ModelMapper().map(data, OrderForm.class);
        form.setPaymentType(data.getPaymentType().getTitle());
        return form;
    }

    public ListData<OrderInfo> getList(OrderSearch search) {
        QOrderInfo orderInfo = QOrderInfo.orderInfo;
        QOrderItem orderItem = QOrderItem.orderItem;
        BooleanBuilder andBuilder = new BooleanBuilder();

        int page = Utils.getNumber(search.getPage(), 1);
        int limit = Utils.getNumber(search.getLimit(), 20);
        int offset = (page - 1) * limit;
        /** 검색 처리 S */
        Long orderNo = search.getId();
        Long[] orderNos = search.getIds();
        OrderStatus status = search.getStatus();
        OrderStatus[] statuses = search.getStatuses();
        String sopt = search.getSopt();
        String skey = search.getSkey();

        /** 주문 번호 검색 처리 - id, ids S */
        if (orderNo != null) andBuilder.and(orderInfo.id.eq(orderNo));
        if (orderNos != null && orderNos.length > 0) andBuilder.and(orderInfo.id.in(orderNos));
        /** 주문 번호 검색 처리 - orderNo, orderNos E */

        /** 주문 상태 검색 처리 - status, statuses S */
        if (status != null && statuses == null) {
            statuses = new OrderStatus[] { status };
        }


        /** 주문 상태 검색 처리 - status, statuses E */
        if (statuses != null && statuses.length > 0) {
            BooleanBuilder subBuilder = new BooleanBuilder();
            subBuilder.and(orderItem.orderInfo.id.eq(orderInfo.id))
                    .and(orderItem.status.in(statuses));

            andBuilder.and(orderInfo.id.in(
                    JPAExpressions.select(orderItem.orderInfo.id)
                            .from(orderItem)
                            .where(subBuilder)
            ));
        }
        /** 키워드 검색 처리 S */
        sopt = Objects.requireNonNullElse(sopt, "all");
        if (skey != null && !skey.isBlank()) {
            skey = skey.trim();

            if (sopt.equals("all")) { // 통합 검색

            } else if (sopt.equals("name")) { // 주문자, 받는사람 이름
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(orderInfo.orderName.contains(skey))
                        .or(orderInfo.receiverName.contains(skey));
                andBuilder.and(orBuilder);
            } else if (sopt.equals("mobile")) { // 주문자, 받는사람 휴대전화번호
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(orderInfo.orderMobile.contains(skey))
                        .or(orderInfo.receiverMobile.contains(skey));
                andBuilder.and(orBuilder);
            } else if (sopt.equals("address")) { // 배송주소
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(orderInfo.zonecode.contains(skey))
                        .or(orderInfo.address.contains(skey))
                        .or(orderInfo.addressSub.contains(skey));
                andBuilder.and(orBuilder);
            } else if (sopt.equals("bookNm")) {
                BooleanBuilder sub = new BooleanBuilder();
                sub.and(orderItem.orderInfo.id.eq(orderInfo.id))
                        .and(orderItem.bookNm.contains(skey));

                andBuilder.and(orderInfo.id.in(
                        JPAExpressions.select(orderItem.orderInfo.id)
                                .from(orderItem)
                                .where(sub)
                ));
            }

        }
        /** 키워드 검색 처리 E */

        /** 검색 처리 E */

        /** 정렬 처리 S */
        // listOrder_DESC,createdAt_ASC
        List<OrderSpecifier> orderSpecifier = new ArrayList<>();
        String sort = search.getSort();
        if (sort != null && !sort.isBlank()) {
            List<String[]> sorts = Arrays.stream(sort.trim().split(","))
                    .map(s -> s.split("_")).toList();
            PathBuilder pathBuilder = new PathBuilder(OrderInfo.class, "orderInfo");

            for (String[] _sort : sorts) {
                Order direction = Order.valueOf(_sort[1].toUpperCase()); // 정렬 방향
                orderSpecifier.add(new OrderSpecifier(direction, pathBuilder.get(_sort[0])));
            }
        }
        /** 정렬 처리 E */

        JPAQueryFactory factory = new JPAQueryFactory(em);
        List<OrderInfo> items = factory.selectFrom(orderInfo)
                .leftJoin(orderInfo.member)
                .fetchJoin()
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
                .fetch();

        items.stream().forEach(this::toAddInfo);

        ListData<OrderInfo> data = new ListData<>();
        data.setContent(items);

        int total = (int)repository.count(andBuilder);
        Pagination pagination = new Pagination(page, total, 10, limit, request);
        data.setPagination(pagination);

        return data;
    }

    private void toAddInfo(OrderInfo orderInfo) {
        List<OrderItem> items = orderInfo.getOrderItems();
        if (items == null || items.isEmpty()) return;

        items.stream().forEach(i -> bookInfoService.addFileInfo(i.getBook()));
    }

    public void updateInfo(OrderForm orderForm) {
        Long orderNo = orderForm.getId();
        QOrderItem orderItem = QOrderItem.orderItem;
        List<OrderItem> items = (List<OrderItem>)itemRepository.findAll(orderItem.orderInfo.id.eq(orderNo));

        items.stream().forEach(i -> bookInfoService.addFileInfo(i.getBook()));
        orderForm.setOrderItems(items);

    }

}
