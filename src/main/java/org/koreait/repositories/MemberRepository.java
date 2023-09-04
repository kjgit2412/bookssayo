package org.koreait.repositories;

import org.koreait.entities.Member;
import org.koreait.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 회원 정보에 관한 레포지토리
 */
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    /**
     * 사용자 아이디로 회원 정보를 조회하는 메서드
     * @param userId 사용자 아이디
     * @return 조회된 회원 정보 객체
     */
    Member findByUserId(String userId);

    /**
     * 사용자 아이디가 존재하는지 여부를 확인하는 메서드
     * @param userId 사용자 아이디
     * @return 존재 여부 (true: 존재함, false: 존재하지 않음)
     */
    default boolean exists(String userId) {
        QMember member = QMember.member;
        return exists(member.userId.eq(userId));
    }
}
