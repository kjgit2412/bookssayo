package org.koreait.models.order;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.Cart;
import org.koreait.repositories.CartRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartDeleteService {

    private final CartRepository repository;
    private final CartService cartService;


    // 장바구니 비우기
    public void deleteAll(String mode) {
        List<Cart> items = cartService.getList(mode);
        repository.deleteAll(items);
        repository.flush();
    }

    /**
     * 장바구니 등록번호로 삭제
     *
     * @param cartNos
     */
    public void delete(List<Long> cartNos) {

        List<Cart> items = cartService.getList(cartNos);
        if (items == null || items.isEmpty()) return;

        repository.deleteAll(items);
        repository.flush();

    }
}
