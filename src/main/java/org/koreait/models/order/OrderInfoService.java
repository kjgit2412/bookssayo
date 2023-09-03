package org.koreait.models.order;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.OrderInfo;
import org.koreait.repositories.order.OrderInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoRepository repository;

    public OrderInfo get(Long id) {
        OrderInfo data = repository.findById(id).orElseThrow(OrderNotFoundException::new);

        return data;
    }
}
