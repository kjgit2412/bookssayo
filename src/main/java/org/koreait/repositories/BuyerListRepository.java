package org.koreait.repositories;

import com.querydsl.core.BooleanBuilder;
import org.koreait.entities.Buyer;
import org.koreait.entities.QBuyer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

public interface BuyerListRepository extends JpaRepository<Buyer, Long>, QuerydslPredicateExecutor<Buyer> {

    // mode : all - 모두, done - 주문완료, undone : 장바구니 상태
    default List<Buyer> getBuyers(String gid, String mode) {
        QBuyer buyer = QBuyer.buyer;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(buyer.gid.eq(gid));

        if (mode.equals("done")) builder.and(buyer.orderDone.eq(true)); // 주문완료
        else if (mode.equals("undone")) builder.and(buyer.orderDone.eq(false)); // 장바구니 상태인 주문

        List<Buyer> items = (List<Buyer>)findAll(builder, Sort.by(asc("createdAt")));

        return items;
    }

    default List<Buyer> getBuyerDone(String gid) {
        return getBuyers(gid, "done");
    }

}

