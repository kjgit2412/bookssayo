package org.koreait.repositories.order;

import org.koreait.entities.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long>, QuerydslPredicateExecutor<OrderInfo> {
}
