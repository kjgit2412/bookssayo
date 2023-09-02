package org.koreait.models.member;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.ListData;
import org.koreait.commons.Pagination;
import org.koreait.commons.constants.Role;
import org.koreait.entities.Book;
import org.koreait.entities.Member;
import org.koreait.entities.QMember;
import org.koreait.models.files.FileInfoService;
import org.koreait.repositories.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberListService {

    private final EntityManager em;
    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;
    private final HttpServletRequest request;

    public ListData<Member> getList(MemberSearch search) {
        QMember member = QMember.member;



        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;
        int page = search.getPage();
        page = page < 1 ? 1 : page;
        int offset = (page - 1) * limit;

        /** 검색 처리 S*/
        BooleanBuilder andBuilder = new BooleanBuilder();


        Role status = search.getStatus();
        List<Role> statuses = search.getStatuses();
        Long userNo = search.getUserNo();
        String sopt = search.getSopt();
        String skey = search.getSkey();

        /** 회원 분류 검색 처리 S*/
        if (status != null) {
            andBuilder.and(member.role.eq(status));
        }

        if (statuses != null && statuses.size() > 0) {
            andBuilder.and(member.role.in(statuses));
        }
        /** 판매 상태 검색 처리 E*/

        /** 사용자 번호*/
        if (userNo != null) {
            andBuilder.and(member.userNo.eq(userNo));
        }

        /** 조건 및 키워드 검색 S*/
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            sopt = sopt.trim();
            skey = skey.trim();

            if (sopt.equals("all")) { // 통합 검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(member.userNo.stringValue()
                                        .contains(skey))
                         .or(member.userNm.containsIgnoreCase(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("userNm")) {
                andBuilder.and(member.userNm.containsIgnoreCase(skey));
            } else if (sopt.equals("userNo")) {
                andBuilder.and(member.userNo.stringValue()
                                            .contains(skey));
            }
        }
        /** 조건 및 키워드 검색 E*/

        /** 정렬 처리 S*/
        // listOrder_DESC,createdAt_ASC
        List<OrderSpecifier> orderSpecifier = new ArrayList<>();
        String sort = search.getSort();
        if (sort != null && !sort.isBlank()) {
            List<String[]> sorts = Arrays.stream(sort.trim()
                                                     .split(","))
                                         .map(s -> s.split("_"))
                                         .toList();
            PathBuilder pathBuilder = new PathBuilder(Book.class, "member");

            for (String[] _sort : sorts) {
                Order direction = Order.valueOf(_sort[1].toUpperCase()); // 정렬 방향
                orderSpecifier.add(new OrderSpecifier(direction, pathBuilder.get(_sort[0])));
            }
        }
        /** 정렬 처리 E*/


        /** 검색 처리 E*/
        JPAQueryFactory factory = new JPAQueryFactory(em);
        List<Member> items=factory.selectFrom(member).stream().toList();

        ListData<Member> data = new ListData<>();
        data.setContent(items);

        /* Todo : 페이징 처리 로직 추가 */
        int total = (int)memberRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, total, 10, 10, request);
        data.setPagination(pagination);


        return data;
    }

}
