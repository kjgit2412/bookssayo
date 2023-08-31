package org.koreait.models.order;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.Utils;
import org.koreait.controllers.orders.CartForm;
import org.koreait.entities.Book;
import org.koreait.entities.Cart;
import org.koreait.models.books.BookInfoService;
import org.koreait.repositories.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartSaveService {
    private final BookInfoService bookInfoService;
    private final CartService cartService;
    private final CartDeleteService cartDeleteService;
    private final MemberUtil memberUtil;
    private final CartRepository repository;
    private final Utils utils;

    public void save(CartForm form) {
        Long bookNo = form.getBookNo();
        String mode = form.getMode();

        // 바로 구매 -> 기존 바로구매 정보 Cart에서 삭제
        if (mode.equals("direct")) {
            cartDeleteService.deleteAll("direct");
        }

        Book book = bookInfoService.get(bookNo);
        Cart cart = mode.equals("direct")?null:cartService.getByBookNo(bookNo);
        System.out.println("cart : " + cart);
        if (cart == null) { // 신규 추가  - 장바구니에 없는 경우 + 바로 구매(direct)
            cart = new ModelMapper().map(form, Cart.class);
            cart.setBook(book);
            cart.setUid(utils.guestUid());

            if (memberUtil.isLogin()) { // 로그인 상태인 경우 회원 엔티티 추가
                cart.setMember(memberUtil.getEntity());
            }
        } else { // 수량 변경 - 장바구니
            cart.setEa(cart.getEa() + form.getEa());
        }

        repository.saveAndFlush(cart);
    }
}
