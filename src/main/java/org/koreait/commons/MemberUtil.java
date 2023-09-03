package org.koreait.commons;

import jakarta.servlet.http.HttpSession;
import org.koreait.commons.constants.Role;
import org.koreait.entities.Member;
import org.koreait.models.member.MemberInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
/**
 * 회원 정보를 다루는 유틸리티 클래스.
 */
public class MemberUtil {

    @Autowired
    private HttpSession session;

    /**
     * 현재 로그인 여부를 판단.
     *
     * @return 로그인 중인 경우 true, 그렇지 않은 경우 false를 반환.
     */
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * 현재 사용자가 관리자 권한을 가지고 있는지 확인.
     *
     * @return 관리자 권한이 있는 경우 true, 아닌 경우 false를 반환.
     */
    public boolean isAdmin() {
        return isLogin() && getMember().getRole() == Role.ADMIN;
    }

    /**
     * 현재 로그인 중인 회원 정보를 가져옴.
     *
     * @return 현재 로그인 중인 회원 정보 객체를 반환.
     */
    public MemberInfo getMember() {
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        return memberInfo;
    }

    /**
     * 현재 로그인 중인 회원 정보를 엔티티 객체로 변환하여 반환.
     *
     * @return 로그인 중인 회원 정보의 엔티티 객체를 반환합니다. 로그인 중이 아닌 경우에는 null을 반환.
     */
    public Member getEntity() {
        if (isLogin()) {
            Member member = new ModelMapper().map(getMember(), Member.class);
            return member;
        }

        return null;
    }
}
