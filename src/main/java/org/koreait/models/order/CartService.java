package org.koreait.models.order;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.Utils;
import org.koreait.entities.Book;
import org.koreait.entities.Cart;
import org.koreait.entities.QCart;
import org.koreait.models.books.BookInfoService;
import org.koreait.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service("cartService2")
@RequiredArgsConstructor
public class CartService {

    private final BookInfoService bookInfoService;
    private final CartRepository repository;
    private final MemberUtil memberUtil;
    private final Utils utils;
    private final JPAQueryFactory factory;

    public Cart getByBookNo(Long bookNo) {
        int uid = utils.guestUid();
        QCart cart = QCart.cart;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cart.book.bookNo.eq(bookNo));
        builder.and(cart.mode.eq("cart"));

        if (memberUtil.isLogin()) {
            Long userNo = memberUtil.getMember().getUserNo();
            builder.and(cart.member.userNo.eq(userNo));
        } else {
            builder.and(cart.uid.eq(uid))
                    .and(cart.member.userNo.isNull());
        }

        return repository.findOne(builder).orElse(null);
    }

    public List<Cart> getList(String mode) {
        return getList(mode, null);
    }

    public List<Cart> getList(List<Long> cartNos) {
        return getList(null, cartNos);
    }

    public List<Cart> getList(String mode, List<Long> cartNos) {

        QCart cart = QCart.cart;
        BooleanBuilder builder = new BooleanBuilder();
        if (mode != null && !mode.isBlank()) builder.and(cart.mode.eq(mode));

        /** 장바구니 등록 번호로 조회 S */
        if (cartNos != null && !cartNos.isEmpty()) {
            builder.and(cart.CartNo.in(cartNos));
        }
        /** 장바구니 등록 번호로 조회 E */

        if (memberUtil.isLogin()) { // 회원
            builder.and(cart.member.userNo.eq(memberUtil.getMember().getUserNo()));
        } else { // 비회원
            builder.and(cart.uid.eq(utils.guestUid()))
                    .and(cart.member.userNo.isNull());
        }

        PathBuilder pathBuilder = new PathBuilder(Cart.class, "cart");

        List<Cart> items =  factory.selectFrom(cart)
                .leftJoin(cart.member)
                .leftJoin(cart.book)
                .fetchJoin()
                .where(builder)
                .orderBy(new OrderSpecifier(Order.ASC, pathBuilder.get("createdAt")))
                .fetch();

        items.stream().forEach(this::addCart);

        return items;

    }

    private void addCart(Cart cart) {
        Book book = cart.getBook();
        if (book != null) bookInfoService.addFileInfo(book);
    }

    public int getTotalPrice(List<Cart> items) {
        items.stream().forEach(item -> item.setTotalPrice(item.getBook().getPrice() * item.getEa()));

        int totalPrice = items.stream().mapToInt(Cart::getTotalPrice).sum()+3000;       // 배송료 포함

        return totalPrice;
    }
}
