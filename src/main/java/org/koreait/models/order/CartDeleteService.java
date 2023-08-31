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

    public void deleteAll(String mode) {
        List<Cart> items = cartService.getList(mode);
        repository.deleteAll(items);
        repository.flush();
    }

}
